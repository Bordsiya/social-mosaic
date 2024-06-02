package com.example.graphservice.repository.postgres

import com.example.graphservice.entity.postgres.GroupThemesRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupThemesRevisionDraftRepository : JpaRepository<GroupThemesRevisionDraft, String> {
}