package com.example.socialmosaiccore.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class CentralityMetric {
    @JsonProperty("DEGREE")
    DEGREE,
    @JsonProperty("CLOSENESS")
    CLOSENESS,
    @JsonProperty("BETWEENNESS")
    BETWEENNESS,
}