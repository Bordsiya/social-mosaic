package com.example.graphservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GraphSettingsResponse(
        @JsonProperty("max_depth")
        val maxDepth: Int,
        @JsonProperty("max_connections_per_layer")
        val maxConnectionsPerLayer: Int,
        @JsonProperty("max_img_themes")
        val maxImgThemes: Int,
        @JsonProperty("max_group_themes")
        val maxGroupThemes: Int,
)
