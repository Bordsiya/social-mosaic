package com.example.graphservice.saga

import com.example.graphservice.entity.postgres.*
import com.example.graphservice.service.*

interface SagaAction {
    suspend fun execute()
    suspend fun compensate()
}

class SaveFriendsRevisionAction(
        private val service: FriendsRevisionDraftService,
        private val userId: String,
        private val settings: String
) : SagaAction {
    private var savedRevision: FriendsRevisionDraft? = null

    fun getSavedRevision(): FriendsRevisionDraft? {
        return savedRevision
    }
    override suspend fun execute() {
        savedRevision = service.save(userId, settings)
    }

    override suspend fun compensate() {
        savedRevision?.let { service.delete(it.id!!) }
    }
}

class SaveImgThemesRevisionAction(
        private val service: ImgThemesRevisionDraftService,
        private val userId: String,
        private val friendsRevisionId: String,
        private val settings: String,
) : SagaAction {
    private var savedRevision: ImgThemesRevisionDraft? = null

    fun getSavedRevision(): ImgThemesRevisionDraft? {
        return savedRevision
    }

    override suspend fun execute() {
        savedRevision = service.save(
                friendsRevisionId = friendsRevisionId,
                userId = userId,
                settings = settings,
        )
    }

    override suspend fun compensate() {
        savedRevision?.let { service.delete(it.id!!) }
    }
}

class SaveGroupThemesRevisionAction(
        private val service: GroupThemesRevisionDraftService,
        private val userId: String,
        private val friendsRevisionId: String,
        private val settings: String,
) : SagaAction {
    private var savedRevision: GroupThemesRevisionDraft? = null

    fun getSavedRevision(): GroupThemesRevisionDraft? {
        return savedRevision
    }

    override suspend fun execute() {
        savedRevision = service.save(
                friendsRevisionId = friendsRevisionId,
                userId = userId,
                settings = settings,
        )
    }

    override suspend fun compensate() {
        savedRevision?.let { service.delete(it.id!!) }
    }
}

class SaveGroupSimilarityRevisionAction(
        private val service: GroupSimilarityRevisionDraftService,
        private val userId: String,
        private val groupThemesRevisionId: String,
        private val settings: String,
) : SagaAction {
    private var savedRevision: GroupSimilarityRevisionDraft? = null

    fun getSavedRevision(): GroupSimilarityRevisionDraft? {
        return savedRevision
    }

    override suspend fun execute() {
        savedRevision = service.save(
                groupThemesRevisionId = groupThemesRevisionId,
                userId = userId,
                settings = settings,
        )
    }

    override suspend fun compensate() {
        savedRevision?.let { service.delete(it.id!!) }
    }
}

class SaveImgSimilarityRevisionAction(
        private val service: ImgSimilarityRevisionDraftService,
        private val userId: String,
        private val imgThemesRevisionId: String,
        private val settings: String,
) : SagaAction {
    private var savedRevision: ImgSimilarityRevisionDraft? = null

    fun getSavedRevision(): ImgSimilarityRevisionDraft? {
        return savedRevision
    }

    override suspend fun execute() {
        savedRevision = service.save(
                imgThemesRevisionId = imgThemesRevisionId,
                userId = userId,
                settings = settings,
        )
    }

    override suspend fun compensate() {
        savedRevision?.let { service.delete(it.id!!) }
    }
}

class AddFriendshipAction(
        private val graphService: GraphService,
        private val user1Id: String,
        private val user2Id: String,
        private val revision: String
) : SagaAction {
    override suspend fun execute() {
        graphService.addFriendshipIfNotExist(user1Id, user2Id, revision)
    }

    override suspend fun compensate() {
        graphService.deleteFriendshipByUserIdsAndRevision(user1Id, user2Id, revision)
    }
}

class AddImgThemesAction(
        private val graphService: GraphService,
        private val userId: String,
        private val friendsRevisionId: String,
        private val imgThemesRevisionId: String,
        private val imgThemes: String,
) : SagaAction {
    override suspend fun execute() {
        graphService.updateImageThemes(
                userId = userId,
                friendsRevision = friendsRevisionId,
                imgThematicRevision = imgThemesRevisionId,
                imgThemes = imgThemes,
        )
    }

    override suspend fun compensate() {
        graphService.removeImageThemes(
                userId = userId,
                friendsRevision = friendsRevisionId,
        )
    }
}

class AddGroupThemesAction(
        private val graphService: GraphService,
        private val userId: String,
        private val friendsRevisionId: String,
        private val groupThemesRevisionId: String,
        private val groupThemes: String,
) : SagaAction {
    override suspend fun execute() {
        graphService.updateGroupThemes(
                userId = userId,
                friendsRevision = friendsRevisionId,
                groupThematicRevision = groupThemesRevisionId,
                groupThemes = groupThemes,
        )
    }

    override suspend fun compensate() {
        graphService.removeGroupThemes(
                userId = userId,
                friendsRevision = friendsRevisionId,
        )
    }
}

class AddImgSimilarityAction(
        private val graphService: GraphService,
        private val userId: String,
        private val friendsRevisionId: String,
        private val imgThemesRevisionId: String,
        private val imgSimilarityRevisionId: String,
) : SagaAction {
    override suspend fun execute() {
        graphService.updateImgSimilarity(
                userId = userId,
                friendsRevision = friendsRevisionId,
                imgThematicRevision = imgThemesRevisionId,
                imgRevision = imgSimilarityRevisionId,
        )
    }

    override suspend fun compensate() {
        graphService.removeImgSimilarity(
                userId = userId,
                friendsRevision = friendsRevisionId,
                imgThematicRevision = imgThemesRevisionId,
                imgRevision = imgSimilarityRevisionId,
        )
    }
}

class AddGroupSimilarityAction(
        private val graphService: GraphService,
        private val userId: String,
        private val friendsRevisionId: String,
        private val groupThemesRevisionId: String,
        private val groupSimilarityRevisionId: String,
) : SagaAction {
    override suspend fun execute() {
        graphService.updateGroupSimilarity(
                userId = userId,
                friendsRevision = friendsRevisionId,
                groupThematicRevision = groupThemesRevisionId,
                groupRevision = groupSimilarityRevisionId,
        )
    }

    override suspend fun compensate() {
        graphService.removeGroupSimilarity(
                userId = userId,
                friendsRevision = friendsRevisionId,
                groupThematicRevision = groupThemesRevisionId,
                groupRevision = groupSimilarityRevisionId,
        )
    }
}