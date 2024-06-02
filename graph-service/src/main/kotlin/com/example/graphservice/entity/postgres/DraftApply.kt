package com.example.graphservice.entity.postgres

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "draft_apply")
@IdClass(DraftApplyId::class)
data class DraftApply(
        @Id
        @Column(name = "id", nullable = false)
        @JsonView
        val id: String? = null,

        @Id
        @Column(name = "user_id", nullable = false)
        @JsonView
        val userId: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "friends_revision", nullable = false)
        val friendsRevision: FriendsRevisionDraft? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "img_themes_revision", nullable = false)
        val imgThemesRevision: ImgThemesRevisionDraft? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_themes_revision", nullable = false)
        val groupThemesRevision: GroupThemesRevisionDraft? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "img_similarity_revision", nullable = false)
        val imgSimilarityRevision: ImgSimilarityRevisionDraft? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_similarity_revision", nullable = false)
        val groupSimilarityRevision: GroupSimilarityRevisionDraft? = null,

        @Column(name = "created_date", nullable = false)
        val createdDate: Instant? = null,
)

data class DraftApplyId(
        val id: String? = null,
        val userId: String? = null,
)