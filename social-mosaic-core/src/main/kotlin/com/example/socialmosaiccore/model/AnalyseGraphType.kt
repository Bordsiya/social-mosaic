package com.example.socialmosaiccore.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class AnalyseGraphType {
    @JsonProperty("ALL")
    ALL,
    @JsonProperty("GROUPS")
    GROUPS,
    @JsonProperty("IMAGES")
    IMAGES,
}