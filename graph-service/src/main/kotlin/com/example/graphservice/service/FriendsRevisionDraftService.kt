package com.example.graphservice.service

import com.example.graphservice.entity.postgres.FriendsRevisionDraft
import com.example.graphservice.repository.postgres.FriendsRevisionDraftRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class FriendsRevisionDraftService(
        private val friendsRevisionDraftRepository: FriendsRevisionDraftRepository,
) {

    fun save(
           userId: String,
           settings: String,
    ): FriendsRevisionDraft {
        return friendsRevisionDraftRepository.save(
                FriendsRevisionDraft(
                        id = UUID.randomUUID().toString(),
                        userId = userId,
                        settings = settings,
                        createdDate = Instant.now(),
                )
        )
    }

    fun delete(id: String) {
        try {
            friendsRevisionDraftRepository.deleteById(id)
        } catch (e: Exception) {
            throw Exception("Failed to delete friends revision draft with id $id", e)
        }
    }
}