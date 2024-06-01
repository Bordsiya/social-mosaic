package com.example.socialmosaiccore.service

import com.example.socialmosaiccore.entity.Request
import com.example.socialmosaiccore.model.*
import com.example.socialmosaiccore.model.input.AnalyseGraphInput
import com.example.socialmosaiccore.model.input.UpdateGraphInput
import com.example.socialmosaiccore.util.ANALYSE_REQUESTS_TOPIC
import com.example.socialmosaiccore.util.UPDATE_REQUESTS_TOPIC
import org.springframework.stereotype.Service

@Service
class GraphService(
        private val processService: ProcessService,
) {
    fun scheduleUpdateGraph(
            userId: String,
            accessToken: String,
            idempotencyToken: String,
            updateType: UpdateGraphType,
            settings: UpdateGraphSettings,
    ): Request {
        val input = UpdateGraphInput(
                updateType = updateType,
                settings = settings,
                idempotencyToken = idempotencyToken,
                processType = ProcessType.UPDATE_GRAPH,
        ).toMap()

        return processService.planProcess(
                userId = userId,
                accessToken = accessToken,
                id = idempotencyToken,
                input = input,
                type = ProcessType.UPDATE_GRAPH,
                idempotencyToken = idempotencyToken,
                queueName = UPDATE_REQUESTS_TOPIC,
        )
    }

    fun scheduleAnalyseGraph(
            userId: String,
            accessToken: String,
            idempotencyToken: String,
            analyseType: AnalyseGraphType,
            settings: AnalyseGraphSettings,
    ): Request {
        val input = AnalyseGraphInput(
                analyseType = analyseType,
                settings = settings,
                idempotencyToken = idempotencyToken,
                processType = ProcessType.ANALYSE_GRAPH,
        ).toMap()

        return processService.planProcess(
                userId = userId,
                accessToken = accessToken,
                id = idempotencyToken,
                input = input,
                type = ProcessType.ANALYSE_GRAPH,
                idempotencyToken = idempotencyToken,
                queueName = ANALYSE_REQUESTS_TOPIC,
        )
    }
}