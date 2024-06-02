package com.example.analyseservice.queue

import com.example.analyseservice.model.response.AnalyseTaskResponse
import com.example.analyseservice.util.ANALYSE_RESPONSES_TOPIC
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
        val kafkaTemplate: KafkaTemplate<String, String>,
        val objectMapper: ObjectMapper,
) {

    fun sendMessage(analyseTaskResponse: AnalyseTaskResponse) {
        kafkaTemplate.send(ANALYSE_RESPONSES_TOPIC, objectMapper.writeValueAsString(analyseTaskResponse))
    }
}