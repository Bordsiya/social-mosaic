package com.example.graphservice.service

import com.example.graphservice.adapter.VkApiAdapter
import com.example.graphservice.entity.neo4j.UserNode
import com.example.graphservice.entity.postgres.FriendsRevisionDraft
import com.example.graphservice.entity.postgres.GroupThemesRevisionDraft
import com.example.graphservice.entity.postgres.ImgThemesRevisionDraft
import com.example.graphservice.model.UpdateSettings
import com.example.graphservice.saga.AddFriendshipAction
import com.example.graphservice.saga.SagaAction
import com.example.graphservice.saga.SaveFriendsRevisionAction
import com.fasterxml.jackson.databind.ObjectMapper
import com.vk.api.sdk.client.actors.UserActor
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

@Service
class UpdateService(
        private val objectMapper: ObjectMapper,
        private val vkApiAdapter: VkApiAdapter,
        private val friendsRevisionDraftService: FriendsRevisionDraftService,
        private val graphService: GraphService,
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
        val queue = Channel<Pair<Long, Int>>(CHANNEL_CAPACITY)
        val visited = ConcurrentHashMap.newKeySet<Long>()

        val graphNodeDescriptors = graphService.findUserFriendsSimilarityGraph(userId.toString(), friendsRevision)

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

            graphNodeDescriptors.forEach { node ->
                queue.send(Pair(node.userId!!.toLong(), 0))
                visited.add(node.userId.toLong())
            }

            withContext(dispatcher + CoroutineName("updateGroupThemesFromGraph")) {
                launch {
                    repeat(COROUTINES_AMOUNT) {
                        launchBFSUpdateFriends(
                                accessToken,
                                queue,
                                visited,
                                updateSettings.maxDepth,
                                updateSettings.maxConnectionsPerLayer,
                                friendsRevision,
                                sagaActions
                        )
                    }
                }

                queue.consumeEach {
                }
            }

            // Ваши действия по завершению обработки, например, сохранение результата
            val draft = SaveFriendsRevisionDraft() // Псевдокод
            return draft
        } catch (e: Exception) {
            sagaActions.toList().asReversed().forEach { it.compensate() }
            throw e
        } finally {
            queue.cancel()
        }
    }

    suspend fun updateImgThemes(
            userId: Long,
            accessToken: String,
            friendsRevision: String,
    ): ImgThemesRevisionDraft {
        val graphNodes = graphService.findUserFriendsSimilarityGraph(
                userId.toString(),
                friendsRevision,
        )
        return ImgThemesRevisionDraft()
    }

    companion object {
        const val CHANNEL_CAPACITY: Int = 30
        const val COROUTINES_AMOUNT: Int = 5
    }
}