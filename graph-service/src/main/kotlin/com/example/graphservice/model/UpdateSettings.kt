package com.example.graphservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateSettings(
        @JsonProperty("max_depth", required = true)
        val maxDepth: Int,
        @JsonProperty("max_connections_per_layer", required = true)
        val maxConnectionsPerLayer: Int,
)
