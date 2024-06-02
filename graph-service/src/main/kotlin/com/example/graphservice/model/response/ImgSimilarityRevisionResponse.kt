package com.example.graphservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ImgSimilarityRevisionResponse(
        @JsonProperty("img_similarity_revision", required = true)
        val imgSimilarityRevision: String,
)
