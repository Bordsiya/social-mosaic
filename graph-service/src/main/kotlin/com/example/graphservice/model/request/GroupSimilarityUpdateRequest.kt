package com.example.graphservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupSimilarityUpdateRequest(
        @JsonProperty("user_id", required = true)
        val userId: String,
        @JsonProperty("friends_revision", required = true)
        val friendsRevision: String,
        @JsonProperty("group_themes_revision", required = true)
        val groupThemesRevision: String,
)
