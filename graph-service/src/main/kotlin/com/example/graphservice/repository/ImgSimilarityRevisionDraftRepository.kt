package com.example.graphservice.repository

import com.example.graphservice.entity.ImgSimilarityRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository

interface ImgSimilarityRevisionDraftRepository : JpaRepository<ImgSimilarityRevisionDraft, String> {
}