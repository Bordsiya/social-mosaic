package com.example.graphservice.entity

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

        @Column(name = "created_date", nullable = false)
        val createdDate: Instant? = null,

        @OneToMany(mappedBy = "imgSimilarityRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val applyDrafts: List<DraftApply> = listOf(),
)
