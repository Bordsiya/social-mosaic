package com.example.graphservice.entity.postgres

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "img_themes_revision_draft")
data class ImgThemesRevisionDraft(
        @Id
        @Column(name = "id", nullable = false)
        @JsonView
        val id: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "friends_revision", nullable = false)
        @JsonView
        val friendsRevision: FriendsRevisionDraft? = null,

        @Column(name = "settings", nullable = false)
        @JsonView
        val settings: String? = null,

        @Column(name = "created_date", nullable = false)
        @JsonView
        val createdDate: Instant? = null,

        @OneToMany(mappedBy = "imgThemesRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val imgSimilarityRevisions: List<ImgSimilarityRevisionDraft> = listOf(),

        @OneToMany(mappedBy = "imgThemesRevision", cascade = [CascadeType.ALL], orphanRemoval = true)
        val draftApplies: List<DraftApply> = listOf()
)
