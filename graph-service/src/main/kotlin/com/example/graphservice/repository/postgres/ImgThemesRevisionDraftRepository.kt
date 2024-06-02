package com.example.graphservice.repository.postgres

import com.example.graphservice.entity.postgres.ImgThemesRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImgThemesRevisionDraftRepository : JpaRepository<ImgThemesRevisionDraft, String> {
}