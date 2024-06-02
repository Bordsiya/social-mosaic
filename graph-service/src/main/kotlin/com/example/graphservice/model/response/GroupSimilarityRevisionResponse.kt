package com.example.graphservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupSimilarityRevisionResponse(
        @JsonProperty("group_similarity_revision", required = true)
        val groupSimilarityRevision: String,
)
