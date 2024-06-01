package com.example.socialmosaiccore.api

import com.example.socialmosaiccore.model.request.AnalyseGraphRequest
import com.example.socialmosaiccore.model.request.UpdateGraphRequest
import com.example.socialmosaiccore.model.response.ProcessCheckResponse
import com.example.socialmosaiccore.model.response.ProcessStartResponse
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
    fun updateGraphCheckPost(
            @RequestParam("request_id") requestId: String,
    ): ResponseEntity<ProcessCheckResponse>
    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/graph/analyse"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun analyseGraphPost(
            @CookieValue(value = "user_id", required = true) userId: String,
            @CookieValue(value = "access_token", required = true) accessToken: String,
            @CookieValue(value = "graph_revision", required = true) graphRevision: String,
            @Valid @RequestBody analyseGraphRequest: AnalyseGraphRequest
    ): ResponseEntity<ProcessStartResponse>
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/graph/analyse/check"],
            params = ["request_id"],
            produces = ["application/json"],
    )
    fun analyseGraphCheckPost(
            @RequestParam("request_id") requestId: String,
    ): ResponseEntity<ProcessCheckResponse>
}