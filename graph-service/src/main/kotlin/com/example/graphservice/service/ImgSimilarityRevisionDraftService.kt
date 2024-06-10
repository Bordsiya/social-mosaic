package com.example.graphservice.service

import com.example.graphservice.entity.postgres.ImgSimilarityRevisionDraft
import com.example.graphservice.exception.ImgSimilarityRevisionDraftNotFoundException
import com.example.graphservice.exception.ImgThemesRevisionDraftNotFoundException
import com.example.graphservice.repository.postgres.ImgSimilarityRevisionDraftRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ImgSimilarityRevisionDraftService(
        private val imgSimilarityRevisionDraftRepository: ImgSimilarityRevisionDraftRepository,
        private val imgThemesRevisionDraftService: ImgThemesRevisionDraftService,
) {
    @Throws(
            ImgSimilarityRevisionDraftNotFoundException::class,
    )
    fun get(
            revision: String
    ): ImgSimilarityRevisionDraft {
        val result = imgSimilarityRevisionDraftRepository.findById(revision)
        if (result.isPresent) {
            return result.get()
        } else {
            throw ImgSimilarityRevisionDraftNotFoundException(
                    revision = revision,
            )
        }
    }

    @Throws(
            ImgThemesRevisionDraftNotFoundException::class,
    )
    fun save(
            imgThemesRevisionId: String,
            userId: String,
            settings: String,
    ): ImgSimilarityRevisionDraft {
        val imgThemesRevision = imgThemesRevisionDraftService.get(
                revision = imgThemesRevisionId,
        )
        return imgSimilarityRevisionDraftRepository.save(
                ImgSimilarityRevisionDraft(
                        id = UUID.randomUUID().toString(),
                        imgThemesRevision = imgThemesRevision,
                        settings = settings,
                        createdDate = Instant.now(),
                )
        )
    }

    fun delete(id: String) {
        imgSimilarityRevisionDraftRepository.deleteById(id)
    }
}