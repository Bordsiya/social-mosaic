package com.example.analyseservice.model.response

import com.example.analyseservice.model.CentralityMetric
import com.fasterxml.jackson.annotation.JsonProperty

data class AnalyticSettingsResponse(
        @JsonProperty("top_cluster_users_amount")
        val topClusterUsersAmount: Int,
        @JsonProperty("recommendations_amount")
        val recommendationsAmount: Int,
        @JsonProperty("top_centrality_users_amount")
        val topCentralityUsersAmount: Int,
        @JsonProperty("centrality_metrics")
        val centralityMetrics: List<CentralityMetric>,
)
