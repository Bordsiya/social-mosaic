package com.example.graphservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GraphRevisionResponse(
        @JsonProperty("graph_revision", required = true)
        val graphRevision: String,
)
