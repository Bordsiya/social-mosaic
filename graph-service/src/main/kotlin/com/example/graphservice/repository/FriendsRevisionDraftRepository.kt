package com.example.graphservice.repository

import com.example.graphservice.entity.FriendsRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository

interface FriendsRevisionDraftRepository : JpaRepository<FriendsRevisionDraft, String> {
}