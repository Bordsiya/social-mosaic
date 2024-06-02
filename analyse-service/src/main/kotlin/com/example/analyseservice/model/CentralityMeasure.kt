package com.example.analyseservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CentralityMeasure(
        @JsonProperty("userId", required = true)
        val userId: String,
        @JsonProperty("link", required = true)
        val link: String,
        @JsonProperty("score", required = true)
        val score: Float,
)
