package com.example.coreservice.queue

import com.example.coreservice.model.response.TaskResponse
import com.example.coreservice.service.ProcessService
import com.example.coreservice.util.ANALYSE_RESPONSES_TOPIC
import com.example.coreservice.util.UPDATE_RESPONSES_TOPIC
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(
        val objectMapper: ObjectMapper,
        val processService: ProcessService,
) {

    @KafkaListener(
            topics = [UPDATE_RESPONSES_TOPIC],
            groupId = "1"
    )
    fun listenUpdateResponses(response: String) {
        logger.info { "update response - $response" }
        val updateResponse = objectMapper.readValue(response, TaskResponse::class.java)

        processService.updateTask(
                requestId = updateResponse.requestId,
                parentId = updateResponse.parentId,
                name = updateResponse.taskName,
                status = updateResponse.taskStatus,
                error = updateResponse.error,
                errorMessage = updateResponse.errorDescription,
                revision = updateResponse.revision,
        )
    }

    @KafkaListener(
            topics = [ANALYSE_RESPONSES_TOPIC],
            groupId = "1"
    )
    fun listenAnalyseResponses(response: String) {
        logger.info { "analyse response - $response" }
        val taskResponse = objectMapper.readValue(response, TaskResponse::class.java)

        processService.updateTask(
                requestId = taskResponse.requestId,
                parentId = taskResponse.parentId,
                name = taskResponse.taskName,
                status = taskResponse.taskStatus,
                error = taskResponse.error,
                errorMessage = taskResponse.errorDescription,
                revision = taskResponse.revision,
        )
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}