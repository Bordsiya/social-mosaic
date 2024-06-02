package com.example.graphservice.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "img_themes_revision_draft")
data class ImgThemesRevisionDraft(
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

        @OneToMany(mappedBy = "imgThemesRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val imgSimilarityRevisions: List<ImgSimilarityRevisionDraft> = listOf(),

        @OneToMany(mappedBy = "imgThemesRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val draftApplies: List<DraftApply> = listOf()
)
