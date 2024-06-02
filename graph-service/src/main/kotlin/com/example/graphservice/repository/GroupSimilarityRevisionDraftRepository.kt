package com.example.graphservice.repository

import com.example.graphservice.entity.GroupSimilarityRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository

interface GroupSimilarityRevisionDraftRepository : JpaRepository<GroupSimilarityRevisionDraft, String> {
}