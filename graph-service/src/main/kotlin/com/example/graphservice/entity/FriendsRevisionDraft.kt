package com.example.graphservice.entity

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "friends_revision_draft")
@IdClass(FriendsRevisionDraftId::class)
data class FriendsRevisionDraft(
        @Id
        @Column(name = "id", nullable = false)
        @JsonView
        val id: String? = null,

        @Id
        @Column(name = "user_id", nullable = false)
        @JsonView
        val userId: String? = null,

        @Column(name = "settings", nullable = false)
        @JsonView
        val settings: String? = null,

        @Column(name = "created_date", nullable = false)
        @JsonView
        val createdDate: Instant? = null,

        @OneToMany(mappedBy = "friendsRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val imgThemesRevisions: List<ImgThemesRevisionDraft> = listOf(),

        @OneToMany(mappedBy = "friendsRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val groupThemesRevisions: List<GroupThemesRevisionDraft> = listOf(),

        @OneToMany(mappedBy = "friendsRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val draftApplies: List<DraftApply> = listOf(),
)

data class FriendsRevisionDraftId(
        val id: String? = null,
        val userId: String? = null,
)