package com.example.graphservice.service

import com.example.graphservice.entity.postgres.FriendsRevisionDraftId
import com.example.graphservice.entity.postgres.GroupThemesRevisionDraft
import com.example.graphservice.exception.FriendsRevisionDraftNotFoundException
import com.example.graphservice.exception.GroupThemesRevisionDraftNotFoundException
import com.example.graphservice.repository.postgres.GroupThemesRevisionDraftRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class GroupThemesRevisionDraftService(
        private val groupThemesRevisionDraftRepository: GroupThemesRevisionDraftRepository,
        private val friendsRevisionDraftService: FriendsRevisionDraftService,
) {

    @Throws(
            GroupThemesRevisionDraftNotFoundException::class,
    )
    fun get(
            revision: String
    ): GroupThemesRevisionDraft {
        val result = groupThemesRevisionDraftRepository.findById(revision)
        if (result.isPresent) {
            return result.get()
        } else {
            throw GroupThemesRevisionDraftNotFoundException(
                    revision = revision,
            )
        }
    }

    @Throws(
            FriendsRevisionDraftNotFoundException::class,
    )
    fun save(
            friendsRevisionId: String,
            userId: String,
            settings: String,
    ): GroupThemesRevisionDraft {
        val friendsRevision = friendsRevisionDraftService.getByRevisionId(
                FriendsRevisionDraftId(
                        id = friendsRevisionId,
                        userId = userId,
                )
        )
        return groupThemesRevisionDraftRepository.save(
                GroupThemesRevisionDraft(
                        id = UUID.randomUUID().toString(),
                        friendsRevision = friendsRevision,
                        settings = settings,
                        createdDate = Instant.now(),
                )
        )
    }

    fun delete(id: String) {
        groupThemesRevisionDraftRepository.deleteById(id)
    }
}