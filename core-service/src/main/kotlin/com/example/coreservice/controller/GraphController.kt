package com.example.coreservice.controller

import com.example.coreservice.api.GraphApi
import com.example.coreservice.model.request.AnalyseGraphRequest
import com.example.coreservice.model.request.UpdateGraphRequest
import com.example.coreservice.model.response.ProcessCheckResponse
import com.example.coreservice.model.response.ProcessStartResponse
import com.example.coreservice.service.GraphService
import com.example.coreservice.service.ProcessService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class GraphController(
        private val graphService: GraphService,
        private val processService: ProcessService,
): GraphApi {

    override fun updateGraphPost(
            userId: String,
            accessToken: String,
            updateGraphRequest: UpdateGraphRequest
    ): ResponseEntity<ProcessStartResponse> {
        val request = graphService.scheduleUpdateGraph(
                userId = userId,
                accessToken = accessToken,
                idempotencyToken = updateGraphRequest.idempotencyToken,
                updateType = updateGraphRequest.updateType,
                settings = updateGraphRequest.settings,
        )

        return ResponseEntity.ok(
                ProcessStartResponse(
                        requestId = request.id!!,
                )
        )
    }

    override fun updateGraphCheck(
            requestId: String,
    ): ResponseEntity<ProcessCheckResponse> {
        val result = processService.checkProcess(
                requestId = requestId,
        )

        return ResponseEntity.ok(
                ProcessCheckResponse(
                        processStatus = result.processStatus!!,
                        revision = result.revision,
                        error = com.example.coreservice.model.Error(
                                code = result.errorCode,
                                message = result.errorMessage,
                        )
                )
        )
    }

    override fun analyseGraphPost(
            userId: String,
            accessToken: String,
            graphRevision: String,
            analyseGraphRequest: AnalyseGraphRequest
    ): ResponseEntity<ProcessStartResponse> {
        val request = graphService.scheduleAnalyseGraph(
                userId = userId,
                accessToken = accessToken,
                idempotencyToken = analyseGraphRequest.idempotencyToken,
                analyseType = analyseGraphRequest.analyseType,
                settings = analyseGraphRequest.settings,
        )

        return ResponseEntity.ok(
                ProcessStartResponse(
                        requestId = request.id!!,
                )
        )
    }

    override fun analyseGraphCheck(
            requestId: String
    ): ResponseEntity<ProcessCheckResponse> {
        val result = processService.checkProcess(
                requestId = requestId,
        )

        return ResponseEntity.ok(
                ProcessCheckResponse(
                        processStatus = result.processStatus!!,
                        revision = result.revision,
                        error = com.example.coreservice.model.Error(
                                code = result.errorCode,
                                message = result.errorMessage,
                        )
                )
        )
    }
}