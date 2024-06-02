package com.example.graphservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class FriendsRevisionResponse(
        @JsonProperty("friends_revision", required = true)
        val friendsRevision: String,
)
