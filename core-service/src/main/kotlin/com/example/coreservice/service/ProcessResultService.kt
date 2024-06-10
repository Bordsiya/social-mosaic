package com.example.coreservice.service

import com.example.coreservice.entity.ProcessResult
import com.example.coreservice.exception.ProcessResultNotFoundException
import com.example.coreservice.exception.RequestNotFoundException
import com.example.coreservice.model.ProcessStatus
import com.example.coreservice.repository.ProcessResultRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProcessResultService(
        private val processResultRepository: ProcessResultRepository,
        private val requestService: RequestService,
) {

    @Throws(
            ProcessResultNotFoundException::class,
    )
    fun get(requestId: String): ProcessResult {
        return processResultRepository.findByIdOrNull(requestId) ?: throw ProcessResultNotFoundException(requestId = requestId)
    }

    @Throws(
            RequestNotFoundException::class,
    )
    fun save(
            requestId: String,
            revision: String? = null,
            processStatus: ProcessStatus,
            error: String? = null,
            errorMessage: String? = null,
    ): ProcessResult {
        val request = requestService.get(requestId)
        return processResultRepository.save(
                ProcessResult(
                        id = UUID.randomUUID().toString(),
                        requestId = request,
                        revision = revision,
                        processStatus = processStatus,
                        errorCode = error,
                        errorMessage = errorMessage,
                )
        )
    }

}