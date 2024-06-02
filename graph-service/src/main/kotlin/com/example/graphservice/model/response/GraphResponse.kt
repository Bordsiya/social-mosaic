package com.example.graphservice.model.response

import com.example.graphservice.model.GraphNode
import com.example.graphservice.model.GraphRelation
import com.fasterxml.jackson.annotation.JsonProperty

data class GraphResponse(
        @JsonProperty("nodes", required = true)
        val nodes: List<GraphNode>,
        @JsonProperty("relationships", required = true)
        val relationships: List<GraphRelation>,
)
