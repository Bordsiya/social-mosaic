package com.example.graphservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ImgThemesRevisionResponse(
        @JsonProperty("img_themes_revision", required = true)
        val imgThemesRevision: String,
)
