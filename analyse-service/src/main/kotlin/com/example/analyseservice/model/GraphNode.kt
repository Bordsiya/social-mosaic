package com.example.analyseservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GraphNode(
        @JsonProperty("id", required = true)
        val id: String,
        @JsonProperty("labels", required = true)
        val labels: List<String>,
        @JsonProperty("properties", required = true)
        val properties: List<GraphProperty>,
)
