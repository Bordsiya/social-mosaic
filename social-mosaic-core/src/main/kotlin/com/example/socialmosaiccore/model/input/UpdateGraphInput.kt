package com.example.socialmosaiccore.model.input

import com.example.socialmosaiccore.model.ProcessType
import com.example.socialmosaiccore.model.UpdateGraphType
import com.example.socialmosaiccore.model.UpdateGraphSettings
import com.example.socialmosaiccore.util.IDEMPOTENCY_TOKEN
import com.example.socialmosaiccore.util.PROCESS_TYPE
import com.example.socialmosaiccore.util.SETTINGS
import com.example.socialmosaiccore.util.UPDATE_TYPE

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
