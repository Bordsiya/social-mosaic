package com.example.coreservice.api

import com.example.coreservice.model.request.AnalyseGraphRequest
import com.example.coreservice.model.request.UpdateGraphRequest
import com.example.coreservice.model.response.ProcessCheckResponse
import com.example.coreservice.model.response.ProcessStartResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Validated
@RequestMapping("/")
interface GraphApi {
    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/graph/update"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun updateGraphPost(
            @CookieValue(value = "user_id", required = true) userId: String,
            @CookieValue(value = "access_token", required = true) accessToken: String,
            @Valid @RequestBody updateGraphRequest: UpdateGraphRequest,
    ): ResponseEntity<ProcessStartResponse>
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/graph/update/check"],
            params = ["request_id"],
            produces = ["application/json"],
    )
    fun updateGraphCheck(
            @RequestParam("request_id") requestId: String,
    ): ResponseEntity<ProcessCheckResponse>
    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/graph/analyse"],
            params = ["graph_revision"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun analyseGraphPost(
            @CookieValue(value = "user_id", required = true) userId: String,
            @CookieValue(value = "access_token", required = true) accessToken: String,
            @RequestParam("graph_revision") graphRevision: String,
            @Valid @RequestBody analyseGraphRequest: AnalyseGraphRequest
    ): ResponseEntity<ProcessStartResponse>
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/graph/analyse/check"],
            params = ["request_id"],
            produces = ["application/json"],
    )
    fun analyseGraphCheck(
            @RequestParam("request_id") requestId: String,
    ): ResponseEntity<ProcessCheckResponse>
}