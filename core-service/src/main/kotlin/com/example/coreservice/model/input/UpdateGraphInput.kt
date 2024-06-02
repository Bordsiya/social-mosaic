package com.example.coreservice.model.input

import com.example.coreservice.model.ProcessType
import com.example.coreservice.model.UpdateGraphType
import com.example.coreservice.model.UpdateGraphSettings
import com.example.coreservice.util.IDEMPOTENCY_TOKEN
import com.example.coreservice.util.PROCESS_TYPE
import com.example.coreservice.util.SETTINGS
import com.example.coreservice.util.UPDATE_TYPE

data class UpdateGraphInput(
        val updateType: UpdateGraphType,
        val settings: UpdateGraphSettings,
        val idempotencyToken: String,
        val processType: ProcessType,
) {
    fun toMap() = mapOf(
            UPDATE_TYPE to updateType,
            SETTINGS to settings,
            IDEMPOTENCY_TOKEN to idempotencyToken,
            PROCESS_TYPE to processType,
    )
}
