package com.example.graphservice.service

import com.example.graphservice.entity.postgres.FriendsRevisionDraft
import com.example.graphservice.entity.postgres.FriendsRevisionDraftId
import com.example.graphservice.exception.FriendsRevisionDraftNotFoundException
import com.example.graphservice.repository.postgres.FriendsRevisionDraftRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class FriendsRevisionDraftService(
        private val friendsRevisionDraftRepository: FriendsRevisionDraftRepository,
) {

    @Throws(
            FriendsRevisionDraftNotFoundException::class,
    )
    fun getByRevisionId(
            revisionDraftId: FriendsRevisionDraftId
    ): FriendsRevisionDraft {
        val result = friendsRevisionDraftRepository.findById(revisionDraftId)
        if (result.isPresent) {
            return result.get()
        } else throw FriendsRevisionDraftNotFoundException(
                userId = revisionDraftId.userId!!,
                revision = revisionDraftId.id!!,
        )
    }

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
        friendsRevisionDraftRepository.deleteById(id)
    }
}