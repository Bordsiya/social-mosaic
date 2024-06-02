package com.example.coreservice.model.input

import com.example.coreservice.model.AnalyseGraphSettings
import com.example.coreservice.model.AnalyseGraphType
import com.example.coreservice.model.ProcessType
import com.example.coreservice.util.ANALYSE_TYPE
import com.example.coreservice.util.IDEMPOTENCY_TOKEN
import com.example.coreservice.util.PROCESS_TYPE
import com.example.coreservice.util.SETTINGS

data class AnalyseGraphInput(
        val analyseType: AnalyseGraphType,
        val settings: AnalyseGraphSettings,
        val idempotencyToken: String,
        val processType:ProcessType,
) {
    fun toMap() = mapOf(
            ANALYSE_TYPE to analyseType,
            SETTINGS to settings,
            IDEMPOTENCY_TOKEN to idempotencyToken,
            PROCESS_TYPE to processType,
    )
}