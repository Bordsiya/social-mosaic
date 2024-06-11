package com.example.analyseservice.model.response

import com.example.analyseservice.model.GraphNode
import com.example.analyseservice.model.GraphRelation
import com.fasterxml.jackson.annotation.JsonProperty

data class GraphResponse(
        @JsonProperty("nodes", required = true)
        val nodes: List<GraphNode>,
        @JsonProperty("relationships", required = true)
        val relationships: List<GraphRelation>,
)

