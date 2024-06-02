package com.example.coreservice.model.request

import com.example.coreservice.model.UpdateGraphType
import com.example.coreservice.model.UpdateGraphSettings
import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateGraphRequest(
        @JsonProperty("idempotency_token", required = true)
        val idempotencyToken: String,
        @JsonProperty("update_type", required = true)
        val updateType: UpdateGraphType,
        @JsonProperty("settings", required = true)
        val settings: UpdateGraphSettings,
)
