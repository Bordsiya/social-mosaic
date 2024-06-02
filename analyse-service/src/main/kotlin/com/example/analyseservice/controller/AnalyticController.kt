package com.example.analyseservice.controller

import com.example.analyseservice.api.AnalyticApi
import com.example.analyseservice.model.response.AnalyticResultResponse
import com.example.analyseservice.model.response.AnalyticRevisionResponse
import com.example.analyseservice.model.response.AnalyticSettingsResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AnalyticController(

): AnalyticApi {
    override fun analyticGet(analyticRevision: String): ResponseEntity<AnalyticResultResponse> {
        TODO("Not yet implemented")
    }

    override fun analyticRevisionGet(graphRevision: String): ResponseEntity<AnalyticRevisionResponse> {
        TODO("Not yet implemented")
    }

    override fun analyticSettingsGet(analyticRevision: String): ResponseEntity<AnalyticSettingsResponse> {
        TODO("Not yet implemented")
    }
}