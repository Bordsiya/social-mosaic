package com.example.graphservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DraftApplyRequest(
        @JsonProperty("user_id", required = true)
        val userId: String,
        @JsonProperty("friends_revision", required = true)
        val friendsRevision: String,
        @JsonProperty("img_themes_revision", required = true)
        val imgThemesRevision: String,
        @JsonProperty("group_themes_revision", required = true)
        val groupThemesRevision: String,
        @JsonProperty("img_similarity_revision", required = true)
        val imgSimilarityRevision: String,
        @JsonProperty("group_similarity_revision", required = true)
        val groupSimilarityRevision: String,
)
