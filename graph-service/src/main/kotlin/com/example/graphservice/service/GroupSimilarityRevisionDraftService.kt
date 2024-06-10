package com.example.graphservice.service

import com.example.graphservice.entity.postgres.GroupSimilarityRevisionDraft
import com.example.graphservice.exception.GroupSimilarityRevisionDraftNotFoundException
import com.example.graphservice.exception.GroupThemesRevisionDraftNotFoundException
import com.example.graphservice.repository.postgres.GroupSimilarityRevisionDraftRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class GroupSimilarityRevisionDraftService(
        private val groupSimilarityRevisionDraftRepository: GroupSimilarityRevisionDraftRepository,
        private val groupThemesRevisionDraftService: GroupThemesRevisionDraftService,
) {
    @Throws(
            GroupSimilarityRevisionDraftNotFoundException::class,
    )
    fun get(
            revision: String
    ): GroupSimilarityRevisionDraft {
        val result = groupSimilarityRevisionDraftRepository.findById(revision)
        if (result.isPresent) {
            return result.get()
        } else {
            throw GroupSimilarityRevisionDraftNotFoundException(
                    revision = revision,
            )
        }
    }

    @Throws(
            GroupThemesRevisionDraftNotFoundException::class,
    )
    fun save(
            groupThemesRevisionId: String,
            userId: String,
            settings: String,
    ): GroupSimilarityRevisionDraft {
        val groupThemesRevision = groupThemesRevisionDraftService.get(
                revision = groupThemesRevisionId,
        )
        return groupSimilarityRevisionDraftRepository.save(
                GroupSimilarityRevisionDraft(
                        id = UUID.randomUUID().toString(),
                        groupThemesRevision = groupThemesRevision,
                        settings = settings,
                        createdDate = Instant.now(),
                )
        )
    }

    fun delete(id: String) {
        groupSimilarityRevisionDraftRepository.deleteById(id)
    }
}