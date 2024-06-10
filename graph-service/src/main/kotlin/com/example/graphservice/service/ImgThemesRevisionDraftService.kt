package com.example.graphservice.service

import com.example.graphservice.entity.postgres.FriendsRevisionDraftId
import com.example.graphservice.entity.postgres.ImgThemesRevisionDraft
import com.example.graphservice.exception.FriendsRevisionDraftNotFoundException
import com.example.graphservice.exception.ImgThemesRevisionDraftNotFoundException
import com.example.graphservice.repository.postgres.ImgThemesRevisionDraftRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ImgThemesRevisionDraftService(
        private val imgThemesRevisionDraftRepository: ImgThemesRevisionDraftRepository,
        private val friendsRevisionDraftService: FriendsRevisionDraftService,
) {

    @Throws(
            ImgThemesRevisionDraftNotFoundException::class,
    )
    fun get(
            revision: String
    ): ImgThemesRevisionDraft {
        val result = imgThemesRevisionDraftRepository.findById(revision)
        if (result.isPresent) {
            return result.get()
        } else {
            throw ImgThemesRevisionDraftNotFoundException(
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
    ): ImgThemesRevisionDraft {
        val friendsRevision = friendsRevisionDraftService.getByRevisionId(
                FriendsRevisionDraftId(
                        id = friendsRevisionId,
                        userId = userId,
                )
        )
        return imgThemesRevisionDraftRepository.save(
                ImgThemesRevisionDraft(
                        id = UUID.randomUUID().toString(),
                        friendsRevision = friendsRevision,
                        settings = settings,
                        createdDate = Instant.now(),
                )
        )
    }

    fun delete(id: String) {
        imgThemesRevisionDraftRepository.deleteById(id)
    }

}