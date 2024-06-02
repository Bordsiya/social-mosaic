package com.example.updateservice.queue

import com.example.updateservice.model.Request
import com.example.updateservice.util.UPDATE_REQUESTS_TOPIC
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(
        val objectMapper: ObjectMapper,
) {

    @KafkaListener(
            topics = [UPDATE_REQUESTS_TOPIC],
            groupId = "1"
    )
    fun listenUpdateResponses(request: String) {
        logger.info { "update request - $request" }
        val updateRequest = objectMapper.readValue(request, Request::class.java)
        // TODO handle update request
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}