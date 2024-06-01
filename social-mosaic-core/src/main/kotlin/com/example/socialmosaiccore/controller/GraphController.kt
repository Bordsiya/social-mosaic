package com.example.socialmosaiccore.controller

import com.example.socialmosaiccore.api.GraphApi
import com.example.socialmosaiccore.model.request.AnalyseGraphRequest
import com.example.socialmosaiccore.model.request.UpdateGraphRequest
import com.example.socialmosaiccore.model.response.ProcessCheckResponse
import com.example.socialmosaiccore.model.response.ProcessStartResponse
import com.example.socialmosaiccore.service.GraphService
import com.example.socialmosaiccore.service.ProcessService
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

    override fun updateGraphCheckPost(
            requestId: String,
    ): ResponseEntity<ProcessCheckResponse> {
        val result = processService.checkProcess(
                requestId = requestId,
        )

        return ResponseEntity.ok(
                ProcessCheckResponse(
                        processStatus = result.processStatus!!,
                        revision = result.revision,
                        error = com.example.socialmosaiccore.model.Error(
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

    override fun analyseGraphCheckPost(
            requestId: String
    ): ResponseEntity<ProcessCheckResponse> {
        val result = processService.checkProcess(
                requestId = requestId,
        )

        return ResponseEntity.ok(
                ProcessCheckResponse(
                        processStatus = result.processStatus!!,
                        revision = result.revision,
                        error = com.example.socialmosaiccore.model.Error(
                                code = result.errorCode,
                                message = result.errorMessage,
                        )
                )
        )
    }
}