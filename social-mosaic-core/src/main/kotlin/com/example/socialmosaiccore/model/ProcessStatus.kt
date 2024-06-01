package com.example.socialmosaiccore.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class ProcessStatus {
    @JsonProperty("SUCCESS")
    SUCCESS,
    @JsonProperty("PENDING")
    PENDING,
    @JsonProperty("FAILED")
    FAILED,
}