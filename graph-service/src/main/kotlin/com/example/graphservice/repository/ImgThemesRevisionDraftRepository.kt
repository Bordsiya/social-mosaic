package com.example.graphservice.repository

import com.example.graphservice.entity.ImgThemesRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository

interface ImgThemesRevisionDraftRepository : JpaRepository<ImgThemesRevisionDraft, String> {
}