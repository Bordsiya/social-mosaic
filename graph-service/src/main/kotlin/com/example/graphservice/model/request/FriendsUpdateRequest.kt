package com.example.graphservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class FriendsUpdateRequest(
        @JsonProperty("user_id", required = true)
        val userId: String,
        @JsonProperty("max_depth", required = true)
        val maxDepth: Int,
        @JsonProperty("max_connections_per_layer", required = true)
        val maxConnectionsPerLayer: Int,
)
