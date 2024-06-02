package com.example.coreservice.queue

import com.example.coreservice.entity.Request
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
        val kafkaTemplate: KafkaTemplate<String, String>,
        val objectMapper: ObjectMapper,
) {

    fun sendMessage(queueName: String, request: Request) {
        kafkaTemplate.send(queueName, objectMapper.writeValueAsString(request))
    }
}