package com.example.graphservice.repository.postgres

import com.example.graphservice.entity.postgres.ImgSimilarityRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImgSimilarityRevisionDraftRepository : JpaRepository<ImgSimilarityRevisionDraft, String> {
}