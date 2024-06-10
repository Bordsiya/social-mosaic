package com.example.graphservice.entity.postgres

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "img_similarity_revision_draft")
data class ImgSimilarityRevisionDraft(
        @Id
        @Column(name = "id", nullable = false)
        val id: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "img_themes_revision", nullable = false)
        val imgThemesRevision: ImgThemesRevisionDraft? = null,

        @Column(name = "settings", nullable = false)
        @JsonView
        val settings: String? = null,

        @Column(name = "created_date", nullable = false)
        val createdDate: Instant? = null,

        @OneToMany(mappedBy = "imgSimilarityRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val applyDrafts: List<DraftApply> = listOf(),
)
