package com.example.coreservice.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class CentralityMetric {
    @JsonProperty("DEGREE")
    DEGREE,
    @JsonProperty("CLOSENESS")
    CLOSENESS,
    @JsonProperty("BETWEENNESS")
    BETWEENNESS,
}