package com.example.analyseservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupRecommendation(
        @JsonProperty("id", required = true)
        val id: String,
        @JsonProperty("link", required = true)
        val link: String,
        @JsonProperty("source", required = true)
        val source: Source,
        @JsonProperty("themes", required = true)
        val themes: List<String>,
)
