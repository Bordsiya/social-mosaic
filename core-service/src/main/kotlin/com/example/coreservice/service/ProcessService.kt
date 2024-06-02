package com.example.coreservice.service

import com.example.coreservice.entity.ProcessResult
import com.example.coreservice.entity.Request
import com.example.coreservice.exception.ProcessResultNotFoundException
import com.example.coreservice.exception.RequestNotFoundException
import com.example.coreservice.model.ProcessType
import com.example.coreservice.model.ProcessStatus
import com.example.coreservice.model.TaskStatus
import com.example.coreservice.queue.KafkaProducer
import com.example.coreservice.util.RESEND_MINUTES_LIMIT
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionTemplate
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class ProcessService(
        private val requestService: RequestService,
        private val processResultService: ProcessResultService,
        private val taskService: TaskService,
        private val objectMapper: ObjectMapper,
        private val queueScheduler: KafkaProducer,
        private val transactionTemplate: TransactionTemplate,
) {

    fun planProcess(
            userId: String,
            accessToken: String,
            id: String,
            input: Map<String, Any?>,
            type: ProcessType,
            idempotencyToken: String,
            queueName: String,
    ) : Request {
        val request = Request(
                id = UUID.randomUUID().toString(),
                createdDate = Instant.now(),
                input = objectMapper.writeValueAsString(input),
                status = 'o',
                type = type.name,
                idempotencyToken = idempotencyToken,
        )
        val requestSaveResult = requestService.save(request)
        if (requestSaveResult.request.status == 'o' &&
                (!requestSaveResult.isExisted || calculateIsResendNeedByTaskTimings(
                        requestSaveResult.request.createdDate!!,
                        requestSaveResult.request.modifiedDate))
                ) {
            queueScheduler.sendMessage(queueName, request)
        }
        return requestSaveResult.request
    }

    fun checkProcess(requestId: String) : ProcessResult {
        return try {
            processResultService.get(requestId)
        } catch (e: ProcessResultNotFoundException) {
            val request = requestService.get(requestId)

            ProcessResult(
                    requestId = request,
                    processStatus = ProcessStatus.PENDING,
            )
        }
    }

    fun updateTask(
            requestId: String,
            parentId: String,
            name: String,
            status: TaskStatus,
            error: String? = null,
            errorMessage: String? = null,
            revision: String? = null,
    ) {
        if (status == TaskStatus.FINISHED && (revision != null || error != null)) {
            // task is fully completed
            transactionTemplate.execute { transactionStatus: TransactionStatus ->
                try {
                    processResultService.save(
                            requestId = requestId,
                            revision = revision,
                            processStatus = if (error == null) ProcessStatus.SUCCESS else ProcessStatus.FAILED,
                            error = error,
                            errorMessage = errorMessage,
                    )
                    requestService.update(
                            requestId = requestId,
                            status = 'c',
                            modifiedDate = Instant.now(),
                    )
                } catch (_: RequestNotFoundException) {
                    logger.info { "Request not found while saving finished task results" }
                    // if request is deleted then all other tasks are deleted too due to orphan arg, then we dont need to save the result
                    transactionStatus.setRollbackOnly()
                }
            }
        } else {
            try {
                taskService.save(
                        parentId = parentId,
                        name = name,
                        status = status,
                        error = error,
                        errorDescription = errorMessage,
                        createdDate = Instant.now(),
                )
            } catch (_: RequestNotFoundException) {
                logger.info { "Request not found while saving medium task results" }
                // if request is deleted then all other tasks are deleted too due to orphan arg, then we dont need to save the result
            }
        }
    }

    private fun calculateIsResendNeedByTaskTimings(createdDate: Instant, modifiedDate: Instant?): Boolean {
        modifiedDate?.let {
            return ChronoUnit.MINUTES.between(modifiedDate, Instant.now()) > RESEND_MINUTES_LIMIT
        }
        return ChronoUnit.MINUTES.between(createdDate, Instant.now()) > RESEND_MINUTES_LIMIT
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}