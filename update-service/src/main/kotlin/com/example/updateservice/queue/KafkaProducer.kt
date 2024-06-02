package com.example.updateservice.queue

import com.example.updateservice.model.UpdateTaskResponse
import com.example.updateservice.util.UPDATE_RESPONSES_TOPIC
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
        val kafkaTemplate: KafkaTemplate<String, String>,
        val objectMapper: ObjectMapper,
) {

    fun sendMessage(request: UpdateTaskResponse) {
        kafkaTemplate.send(UPDATE_RESPONSES_TOPIC, objectMapper.writeValueAsString(request))
    }
}