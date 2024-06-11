package com.example.analyseservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GraphProperty(
        @JsonProperty("name", required = true)
        val name: String,
        @JsonProperty("value", required = true)
        val value: String,
)

