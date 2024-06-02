package com.example.analyseservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Source(
        @JsonProperty("userId", required = true)
        val userId: String,
        @JsonProperty("link", required = true)
        val link: String,
)
