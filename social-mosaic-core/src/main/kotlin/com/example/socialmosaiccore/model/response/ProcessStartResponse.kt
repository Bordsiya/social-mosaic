package com.example.socialmosaiccore.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ProcessStartResponse(
        @JsonProperty("request_id", required = true)
        val requestId: String,
)
