package com.example.socialmosaiccore.model.input

import com.example.socialmosaiccore.model.AnalyseGraphSettings
import com.example.socialmosaiccore.model.AnalyseGraphType
import com.example.socialmosaiccore.model.ProcessType
import com.example.socialmosaiccore.util.ANALYSE_TYPE
import com.example.socialmosaiccore.util.IDEMPOTENCY_TOKEN
import com.example.socialmosaiccore.util.PROCESS_TYPE
import com.example.socialmosaiccore.util.SETTINGS

data class AnalyseGraphInput(
        val analyseType: AnalyseGraphType,
        val settings: AnalyseGraphSettings,
        val idempotencyToken: String,
        val processType: ProcessType,
) {
    fun toMap() = mapOf(
            ANALYSE_TYPE to analyseType,
            SETTINGS to settings,
            IDEMPOTENCY_TOKEN to idempotencyToken,
            PROCESS_TYPE to processType,
    )
}