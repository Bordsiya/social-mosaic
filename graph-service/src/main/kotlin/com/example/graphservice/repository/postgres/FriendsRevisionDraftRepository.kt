package com.example.graphservice.repository.postgres

import com.example.graphservice.entity.postgres.FriendsRevisionDraft
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendsRevisionDraftRepository : JpaRepository<FriendsRevisionDraft, String> {
}