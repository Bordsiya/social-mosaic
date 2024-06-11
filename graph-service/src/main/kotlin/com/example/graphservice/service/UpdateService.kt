package com.example.graphservice.service

import com.example.graphservice.adapter.VkApiAdapter
import com.example.graphservice.entity.neo4j.UserNode
import com.example.graphservice.entity.postgres.*
import com.example.graphservice.model.UpdateSettings
import com.example.graphservice.saga.*
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vk.api.sdk.client.actors.UserActor
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import org.springframework.stereotype.Service
import java.net.URL
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

@Service
class UpdateService(
        private val objectMapper: ObjectMapper,
        private val vkApiAdapter: VkApiAdapter,
        private val friendsRevisionDraftService: FriendsRevisionDraftService,
        private val groupThemesRevisionDraftService: GroupThemesRevisionDraftService,
        private val imgThemesRevisionDraftService: ImgThemesRevisionDraftService,
        private val groupSimilarityRevisionDraftService: GroupSimilarityRevisionDraftService,
        private val imgSimilarityRevisionDraftService: ImgSimilarityRevisionDraftService,
        private val graphService: GraphService,
        private val imageAnalysisService: ImageAnalysisService,
) {

    suspend fun updateFriends(
            userId: Long,
            accessToken: String,
            dispatcher: CoroutineDispatcher,
            updateSettings: UpdateSettings,
    ): FriendsRevisionDraft {
        val sagaActions = ConcurrentLinkedQueue<SagaAction>()
        val queue = Channel<Pair<Long, Int>>(CHANNEL_CAPACITY)
        try {
            val saveAction = SaveFriendsRevisionAction(
                    service = friendsRevisionDraftService,
                    userId = userId.toString(),
                    settings = objectMapper.writeValueAsString(updateSettings)
            )
            saveAction.execute()
            sagaActions.add(saveAction)

            val friendsRevisionDraft = saveAction.getSavedRevision()
                    ?: throw IllegalStateException("No revision draft was saved.")

            val actor = UserActor(userId, accessToken)
            val visited = ConcurrentHashMap.newKeySet<Long>()

            visited.add(userId)
            queue.send(Pair(userId, 0))

            withContext(dispatcher + CoroutineName("updateFriends")) {
                launch {
                    repeat(COROUTINES_AMOUNT) {
                        launchUpdateFriends(
                                actor,
                                queue,
                                visited,
                                updateSettings.maxConnectionsPerLayer,
                                updateSettings.maxDepth,
                                friendsRevisionDraft.id!!,
                                sagaActions
                        )
                    }
                }

                queue.consumeEach {  // Proper consumption of the channel entries
                    // No operation needed here, just consuming.
                }
            }

            return friendsRevisionDraft
        } catch (e: Exception) {
            sagaActions.toList().asReversed().forEach { it.compensate() }
            throw e
        } finally {
            queue.cancel()
        }
    }

    private fun CoroutineScope.launchUpdateFriends(
            actor: UserActor,
            queue: Channel<Pair<Long, Int>>,
            visited: MutableSet<Long>,
            maxDepth: Int,
            friendsAmount: Int,
            friendsRevisionId: String,
            sagaActions: ConcurrentLinkedQueue<SagaAction>
    ) = launch {
        for ((userId, depth) in queue) {
            if (depth >= maxDepth || !visited.add(userId)) continue

            val friendsIds = vkApiAdapter.getUserFriendsIds(
                    userActor = actor,
                    friendsAmount = friendsAmount,
            )

            friendsIds.forEach { friendId ->
                launch {
                    val addAction = AddFriendshipAction(
                            graphService = graphService,
                            user1Id = userId.toString(),
                            user2Id = friendId.toString(),
                            revision = friendsRevisionId,
                    )
                    addAction.execute()
                    sagaActions.add(addAction)
                }

                if (depth + 1 < maxDepth && visited.add(friendId)) {
                    queue.send(Pair(friendId, depth + 1))
                }
            }
        }
    }

    suspend fun updateGroupThemes(
            userId: Long,
            accessToken: String,
            friendsRevision: String,
            dispatcher: CoroutineDispatcher,
            groupsAmount: Int,
    ): GroupThemesRevisionDraft {
        val sagaActions = ConcurrentLinkedQueue<SagaAction>()
        val queue = Channel<Long>(CHANNEL_CAPACITY)

        val graphNodeDescriptors = graphService.findUserFriendsSimilarityGraph(userId.toString(), friendsRevision)

        try {
            val saveAction = SaveGroupThemesRevisionAction(
                    service = groupThemesRevisionDraftService,
                    userId = userId.toString(),
                    friendsRevisionId = friendsRevision,
                    settings = objectMapper.writeValueAsString(groupsAmount)
            )
            saveAction.execute()
            sagaActions.add(saveAction)

            val groupThemesRevisionDraft = saveAction.getSavedRevision()
                    ?: throw IllegalStateException("No revision draft was saved.")

            val actor = UserActor(userId, accessToken)
            withContext(dispatcher) {
                launchUpdateGroupThemes(
                        actor,
                        queue,
                        groupsAmount,
                        friendsRevision,
                        groupThemesRevisionDraft.id!!,
                        sagaActions,
                        graphNodeDescriptors,
                )
            }

            return groupThemesRevisionDraft
        } catch (e: Exception) {
            sagaActions.toList().asReversed().forEach { it.compensate() }
            throw e
        } finally {
            queue.cancel()
        }
    }

    private fun CoroutineScope.launchUpdateGroupThemes(
            actor: UserActor,
            queue: Channel<Long>,
            groupsAmount: Int,
            friendsRevisionId: String,
            groupThemesRevisionId: String,
            sagaActions: ConcurrentLinkedQueue<SagaAction>,
            graphNodeDescriptors: List<UserNode>,
    ) {
        launch {
            populateQueue(queue, graphNodeDescriptors)
        }

        repeat(COROUTINES_AMOUNT) {
            launch {
                for (userId in queue) {
                    val groupThemes = vkApiAdapter.getUserGroupsThematics(actor, groupsAmount)
                    val addAction = AddGroupThemesAction(graphService, userId.toString(), friendsRevisionId, groupThemesRevisionId, objectMapper.writeValueAsString(groupThemes))
                    addAction.execute()
                    sagaActions.add(addAction)
                }
            }
        }
    }

    suspend fun updateImgThemes(
            userId: Long,
            accessToken: String,
            friendsRevision: String,
            imgsAmount: Int,
            dispatcher: CoroutineDispatcher,
    ): ImgThemesRevisionDraft {
        val sagaActions = ConcurrentLinkedQueue<SagaAction>()
        val queue = Channel<Long>(CHANNEL_CAPACITY)

        val graphNodeDescriptors = graphService.findUserFriendsSimilarityGraph(userId.toString(), friendsRevision)

        try {
            val saveAction = SaveImgThemesRevisionAction(
                    service = imgThemesRevisionDraftService,
                    userId = userId.toString(),
                    friendsRevisionId = friendsRevision,
                    settings = objectMapper.writeValueAsString(imgsAmount)
            )
            saveAction.execute()
            sagaActions.add(saveAction)

            val imgThemesRevisionDraft = saveAction.getSavedRevision()
                    ?: throw IllegalStateException("No revision draft was saved.")

            val actor = UserActor(userId, accessToken)
            withContext(dispatcher) {
                launchUpdateImgThemes(
                        actor,
                        queue,
                        imgsAmount,
                        friendsRevision,
                        imgThemesRevisionDraft.id!!,
                        sagaActions,
                        graphNodeDescriptors,
                )
            }

            return imgThemesRevisionDraft
        } catch (e: Exception) {
            sagaActions.toList().asReversed().forEach { it.compensate() }
            throw e
        } finally {
            queue.cancel()
        }
    }

    private fun CoroutineScope.launchUpdateImgThemes(
            actor: UserActor,
            queue: Channel<Long>,
            imgAmount: Int,
            friendsRevisionId: String,
            imgThemesRevisionId: String,
            sagaActions: ConcurrentLinkedQueue<SagaAction>,
            graphNodeDescriptors: List<UserNode>,
    ) {
        launch {
            populateQueue(queue, graphNodeDescriptors)
        }

        repeat(COROUTINES_AMOUNT) {
            launch {
                for (userId in queue) {
                    val imgUrls = vkApiAdapter.getUserProfilePhotos(
                            userActor = actor,
                            photosAmount = imgAmount,
                    )
                    imgUrls.forEach { imageUrl ->
                        val themes = imageAnalysisService.classifyImage(imgAmount, URL(imageUrl.toURL().toString()))
                        val addAction = AddImgThemesAction(graphService, userId.toString(), friendsRevisionId, imgThemesRevisionId, objectMapper.writeValueAsString(themes))
                        addAction.execute()
                        sagaActions.add(addAction)
                    }
                }
            }
        }
    }

    suspend fun updateGroupSimilarity(
            userId: Long,
            friendsRevision: String,
            groupThemesRevisionId: String,
            dispatcher: CoroutineDispatcher,
            groupThemesAmount: Int,
    ): GroupSimilarityRevisionDraft {
        val sagaActions = ConcurrentLinkedQueue<SagaAction>()
        val graphNodeDescriptors = graphService.findUserFriendsWithGroupThematicRevision(
                userId = userId.toString(),
                friendsRevision = friendsRevision,
                groupThematicRevision = groupThemesRevisionId,
        )
        try {
            val saveAction = SaveGroupSimilarityRevisionAction(
                    service = groupSimilarityRevisionDraftService,
                    userId = userId.toString(),
                    groupThemesRevisionId = groupThemesRevisionId,
                    settings = objectMapper.writeValueAsString(groupThemesAmount)
            )
            saveAction.execute()
            sagaActions.add(saveAction)

            val groupSimilarityRevisionDraft = saveAction.getSavedRevision()
                    ?: throw IllegalStateException("No revision draft was saved.")

            withContext(dispatcher) {
                processGroupNodes(graphNodeDescriptors, sagaActions, friendsRevision, groupThemesRevisionId)
            }

            return groupSimilarityRevisionDraft
        } catch (e: Exception) {
            sagaActions.toList().asReversed().forEach { it.compensate() }
            throw e
        }
    }
    private suspend fun processGroupNodes(
            graphNodes: List<UserNode>,
            sagaActions: ConcurrentLinkedQueue<SagaAction>,
            friendsRevision: String,
            groupThemesRevisionId: String,
    ) {
        graphNodes.forEach { userNode ->
            userNode.friendsSimilarities.forEach { edge ->
                val userThemesVector: List<Double> = parseThemes(userNode.groupThemes)
                val friendThemesVector: List<Double> = parseThemes(edge.user.groupThemes)
                val similarity = calculateJaccardSimilarity(userThemesVector, friendThemesVector)

                val addAction = AddGroupSimilarityAction(
                        graphService, userNode.userId!!,
                        edge.user.userId!!, friendsRevision, groupThemesRevisionId, objectMapper.writeValueAsString(similarity))
                addAction.execute()
                sagaActions.add(addAction)
            }
        }
    }

    fun calculateJaccardSimilarity(setA: List<Double>, setB: List<Double>): Double {
        val intersection = setA.intersect(setB).size
        val union = setA.union(setB).size
        return if (union == 0) 0.0 else intersection.toDouble() / union.toDouble()
    }

    private fun parseThemes(themesJson: String?): List<Double> {
        return ObjectMapper().readValue(themesJson, object : TypeReference<List<Double>>() {})
    }


    suspend fun updateImgSimilarity(
            userId: Long,
            friendsRevision: String,
            imgThemesRevisionId: String,
            dispatcher: CoroutineDispatcher,
            imgThemesAmount: Int,
    ): ImgSimilarityRevisionDraft {
        val sagaActions = ConcurrentLinkedQueue<SagaAction>()
        val graphNodeDescriptors = graphService.findUserFriendsWithImgThematicRevision(
                userId = userId.toString(),
                friendsRevision = friendsRevision,
                imgThematicRevision = imgThemesRevisionId,
        )
        try {
            val saveAction = SaveImgSimilarityRevisionAction(
                    service = imgSimilarityRevisionDraftService,
                    userId = userId.toString(),
                    imgThemesRevisionId = imgThemesRevisionId,
                    settings = objectMapper.writeValueAsString(imgThemesAmount)
            )
            saveAction.execute()
            sagaActions.add(saveAction)

            val imgSimilarityRevisionDraft = saveAction.getSavedRevision()
                    ?: throw IllegalStateException("No revision draft was saved.")



            return imgSimilarityRevisionDraft
        } catch (e: Exception) {
            sagaActions.toList().asReversed().forEach { it.compensate() }
            throw e
        }
    }

    private suspend fun processImgNodes(
            graphNodes: List<UserNode>,
            sagaActions: ConcurrentLinkedQueue<SagaAction>,
            friendsRevision: String,
            imgThemesRevisionId: String,
    ) {
        graphNodes.forEach { userNode ->
            userNode.friendsSimilarities.forEach { edge ->
                val userThemesVector: List<Double> = parseThemes(userNode.imgThemes)
                val friendThemesVector: List<Double> = parseThemes(edge.user.imgThemes)
                val similarity = calculateJaccardSimilarity(userThemesVector, friendThemesVector)

                val addAction = AddImgSimilarityAction(
                        graphService, userNode.userId!!,
                        edge.user.userId!!, friendsRevision, imgThemesRevisionId, objectMapper.writeValueAsString(similarity))
                addAction.execute()
                sagaActions.add(addAction)
            }
        }
    }

    private fun CoroutineScope.populateQueue(
            queue: Channel<Long>,
            graphNodeDescriptors: List<UserNode>,
    ) {
        graphNodeDescriptors.chunked(CHUNK_SIZE).let { chunkedList ->
            repeat(COROUTINES_AMOUNT) { workerIndex ->
                launch {
                    for (chunkIndex in workerIndex until chunkedList.size step COROUTINES_AMOUNT) {
                        val chunk = chunkedList[chunkIndex]
                        chunk.forEach { node ->
                            queue.send(node.userId!!.toLong())
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val CHANNEL_CAPACITY: Int = 30
        const val COROUTINES_AMOUNT: Int = 5
        const val CHUNK_SIZE: Int = 10
    }
}