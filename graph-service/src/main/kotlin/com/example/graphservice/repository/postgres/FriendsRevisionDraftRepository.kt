package com.example.graphservice.repository.postgres

import com.example.graphservice.entity.postgres.FriendsRevisionDraft
import com.example.graphservice.entity.postgres.FriendsRevisionDraftId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FriendsRevisionDraftRepository : JpaRepository<FriendsRevisionDraft, String> {
    fun findById(id: FriendsRevisionDraftId): Optional<FriendsRevisionDraft>
}