package com.example.socialmosaiccore.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Error(
        @JsonProperty("code")
        val code: String? = null,
        @JsonProperty("message")
        val message: String? = null,
)
