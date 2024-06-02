package com.example.analyseservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AnalyticRevisionResponse(
        @JsonProperty("analytic_revision", required = true)
        val analyticRevision: String,
)
