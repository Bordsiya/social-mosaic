package com.example.graphservice.repository

import com.example.graphservice.entity.GroupThemesRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository

interface GroupThemesRevisionDraftRepository : JpaRepository<GroupThemesRevisionDraft, String> {
}