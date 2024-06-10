package com.example.analyseservice.controller

import com.example.analyseservice.api.AnalyticApi
import com.example.analyseservice.model.response.AnalyticResultResponse
import com.example.analyseservice.model.response.AnalyticRevisionResponse
import com.example.analyseservice.model.response.AnalyticSettingsResponse
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AnalyticController(

): AnalyticApi {
    override fun analyticGet(analyticRevision: String): ResponseEntity<AnalyticResultResponse> {
        logger.info { "analytic/get" }
        TODO("Not yet implemented")
    }

    override fun analyticRevisionGet(graphRevision: String): ResponseEntity<AnalyticRevisionResponse> {
        logger.info { "analytic/revision/get" }
        TODO("Not yet implemented")
    }

    override fun analyticSettingsGet(analyticRevision: String): ResponseEntity<AnalyticSettingsResponse> {
        logger.info { "analytic/settings/get" }
        TODO("Not yet implemented")
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}