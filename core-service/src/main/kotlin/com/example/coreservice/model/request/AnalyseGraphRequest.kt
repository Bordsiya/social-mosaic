package com.example.coreservice.model.request

import com.example.coreservice.model.AnalyseGraphSettings
import com.example.coreservice.model.AnalyseGraphType
import com.fasterxml.jackson.annotation.JsonProperty

data class AnalyseGraphRequest(
        @JsonProperty("idempotency_token", required = true)
        val idempotencyToken: String,
        @JsonProperty("analyse_type", required = true)
        val analyseType: AnalyseGraphType,
        @JsonProperty("settings", required = true)
        val settings: AnalyseGraphSettings,
)
