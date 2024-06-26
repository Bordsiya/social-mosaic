package com.example.graphservice.entity.postgres

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "group_similarity_revision_draft")
data class GroupSimilarityRevisionDraft(
        @Id
        @Column(name = "id", nullable = false)
        @JsonView
        val id: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_themes_revision", nullable = false)
        @JsonView
        val groupThemesRevision: GroupThemesRevisionDraft? = null,

        @Column(name = "settings", nullable = false)
        @JsonView
        val settings: String? = null,

        @Column(name = "created_date", nullable = false)
        @JsonView
        val createdDate: Instant? = null,

        @OneToMany(mappedBy = "groupSimilarityRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val draftApplies: List<DraftApply> = listOf()
)
