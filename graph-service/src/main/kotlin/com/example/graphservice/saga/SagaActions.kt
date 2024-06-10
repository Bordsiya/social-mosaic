package com.example.graphservice.saga

import com.example.graphservice.entity.postgres.FriendsRevisionDraft
import com.example.graphservice.service.FriendsRevisionDraftService
import com.example.graphservice.service.GraphService

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