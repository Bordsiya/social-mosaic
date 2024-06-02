package com.example.graphservice.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class GraphType {
    @JsonProperty("FRIENDS", required = true)
    FRIENDS,
    @JsonProperty("GROUPS", required = true)
    GROUPS,
    @JsonProperty("IMAGES", required = true)
    IMAGES,
}