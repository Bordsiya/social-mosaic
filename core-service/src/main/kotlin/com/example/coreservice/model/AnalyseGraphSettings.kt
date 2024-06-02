package com.example.coreservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AnalyseGraphSettings(
        @JsonProperty("top_cluster_users_amount")
        val topClusterUsersAmount: Int,
        @JsonProperty("recommendations_amount")
        val recommendationsAmount: Int,
        @JsonProperty("top_centrality_users_amount")
        val topCentralityUsersAmount: Int,
        @JsonProperty("centrality_metrics")
        val centralityMetrics: List<CentralityMetric>,
)
