package com.example.graphservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupThemesRevisionResponse(
        @JsonProperty("group_themes_revision", required = true)
        val groupThemesRevision: String,
)
