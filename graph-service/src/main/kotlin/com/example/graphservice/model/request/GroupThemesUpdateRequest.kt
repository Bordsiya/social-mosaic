package com.example.graphservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupThemesUpdateRequest (
        @JsonProperty("user_id", required = true)
        val userId: String,
        @JsonProperty("friends_revision", required = true)
        val friendsRevision: String,
        @JsonProperty("max_group_themes", required = true)
        val maxGroupThemes: Int,
)