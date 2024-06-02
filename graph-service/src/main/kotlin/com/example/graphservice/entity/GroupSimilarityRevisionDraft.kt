package com.example.graphservice.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "group_similarity_revision_draft")
data class GroupSimilarityRevisionDraft(
        @Id
        @Column(name = "id", nullable = false)
        val id: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_themes_revision", nullable = false)
        val groupThemesRevision: GroupThemesRevisionDraft? = null,

        @Column(name = "created_date", nullable = false)
        val createdDate: Instant? = null,

        @OneToMany(mappedBy = "groupSimilarityRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val draftApplies: List<DraftApply> = listOf()
)
