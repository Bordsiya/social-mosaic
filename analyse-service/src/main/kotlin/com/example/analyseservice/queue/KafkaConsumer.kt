package com.example.analyseservice.queue

import com.example.analyseservice.model.Request
import com.example.analyseservice.util.ANALYSE_REQUESTS_TOPIC
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(
        val objectMapper: ObjectMapper,
) {

    @KafkaListener(
            topics = [ANALYSE_REQUESTS_TOPIC],
            groupId = "1"
    )
    fun listenAnalyseRequests(request: String) {
        logger.info { "analyse request - $request" }
        val analyseRequest = objectMapper.readValue(request, Request::class.java)
        // TODO handle analyse request
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}