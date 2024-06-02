package com.example.graphservice.repository.postgres

import com.example.graphservice.entity.postgres.GroupSimilarityRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupSimilarityRevisionDraftRepository : JpaRepository<GroupSimilarityRevisionDraft, String> {
}