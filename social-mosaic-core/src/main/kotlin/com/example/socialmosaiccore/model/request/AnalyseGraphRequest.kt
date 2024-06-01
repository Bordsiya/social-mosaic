package com.example.socialmosaiccore.model.request

import com.example.socialmosaiccore.model.AnalyseGraphSettings
import com.example.socialmosaiccore.model.AnalyseGraphType
import com.fasterxml.jackson.annotation.JsonProperty

data class AnalyseGraphRequest(
        @JsonProperty("idempotency_token", required = true)
        val idempotencyToken: String,
        @JsonProperty("analyse_type", required = true)
        val analyseType: AnalyseGraphType,
        @JsonProperty("settings", required = true)
        val settings: AnalyseGraphSettings,
)
