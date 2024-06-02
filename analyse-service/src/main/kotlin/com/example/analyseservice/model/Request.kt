package com.example.analyseservice.model

import java.time.Instant

data class Request(
        val id: String,
        val createdDate: Instant,
        val input: String,
        val modifiedDate: Instant? = null,
        val status: Char,
        val type: String,
        val idempotencyToken: String,
        val parentTask: String? = null,
)
