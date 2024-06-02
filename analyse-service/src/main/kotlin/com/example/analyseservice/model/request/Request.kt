package com.example.analyseservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class Request(
        @JsonProperty("id", required = true)
        val id: String,
        @JsonProperty("created_date", required = true)
        val createdDate: Instant,
        @JsonProperty("input", required = true)
        val input: String,
        @JsonProperty("modified_date")
        val modifiedDate: Instant? = null,
        @JsonProperty("status", required = true)
        val status: Char,
        @JsonProperty("type", required = true)
        val type: String,
        @JsonProperty("idempotency_token", required = true)
        val idempotencyToken: String,
        @JsonProperty("parent_task")
        val parentTask: String? = null,
)
