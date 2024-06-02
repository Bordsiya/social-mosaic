package com.example.coreservice.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class AnalyseGraphType {
    @JsonProperty("ALL")
    ALL,
    @JsonProperty("GROUPS")
    GROUPS,
    @JsonProperty("IMAGES")
    IMAGES,
}