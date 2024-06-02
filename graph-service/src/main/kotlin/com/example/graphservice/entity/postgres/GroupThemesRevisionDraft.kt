package com.example.graphservice.entity.postgres

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "group_themes_revision_draft")
data class GroupThemesRevisionDraft(
        @Id
        @Column(name = "id", nullable = false)
        val id: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "friends_revision", nullable = false)
        val friendsRevision: FriendsRevisionDraft? = null,

        @Column(name = "settings", nullable = false)
        val settings: String? = null,

        @Column(name = "created_date", nullable = false)
        val createdDate: Instant? = null,

        @OneToMany(mappedBy = "groupThemesRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val groupSimilarityRevisions: List<GroupSimilarityRevisionDraft>? = null,

        @OneToMany(mappedBy = "groupThemesRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val draftApplies: List<DraftApply> = listOf()
)
