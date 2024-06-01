package com.example.socialmosaiccore.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class UpdateGraphType {
    @JsonProperty("ALL")
    ALL,
    @JsonProperty("IMAGES")
    IMAGES,
    @JsonProperty("GROUPS")
    GROUPS,
}