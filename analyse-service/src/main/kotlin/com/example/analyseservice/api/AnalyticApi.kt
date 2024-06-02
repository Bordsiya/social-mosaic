package com.example.analyseservice.api

import com.example.analyseservice.model.response.AnalyticResultResponse
import com.example.analyseservice.model.response.AnalyticRevisionResponse
import com.example.analyseservice.model.response.AnalyticSettingsResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Validated
@RequestMapping("/")
interface AnalyticApi {
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/analytic"],
            params = ["analytic_revision"],
            produces = ["application/json"],
    )
    fun analyticGet(
            @RequestParam("analytic_revision") analyticRevision: String
    ): ResponseEntity<AnalyticResultResponse>

    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/analytic/revision"],
            params = ["graph_revision"],
            produces = ["application/json"],
    )
    fun analyticRevisionGet(
            @RequestParam("graph_revision") graphRevision: String
    ): ResponseEntity<AnalyticRevisionResponse>

    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/analytic/setting"],
            params = ["analytic_revision"],
            produces = ["application/json"],
    )
    fun analyticSettingsGet(
            @RequestParam("analytic_revision") analyticRevision: String
    ): ResponseEntity<AnalyticSettingsResponse>
}