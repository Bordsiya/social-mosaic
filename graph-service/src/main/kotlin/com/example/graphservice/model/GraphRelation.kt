package com.example.graphservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GraphRelation(
        @JsonProperty("id", required = true)
        val id: String,
        @JsonProperty("type", required = true)
        val type: String,
        @JsonProperty("start_node_id", required = true)
        val startNodeId: String,
        @JsonProperty("end_node_id", required = true)
        val endNodeId: String,
        @JsonProperty("properties", required = true)
        val properties: List<GraphProperty>,
)
