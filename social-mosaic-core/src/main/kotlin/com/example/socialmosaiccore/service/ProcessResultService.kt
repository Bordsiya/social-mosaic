package com.example.socialmosaiccore.service

import com.example.socialmosaiccore.entity.ProcessResult
import com.example.socialmosaiccore.exception.ProcessResultNotFoundException
import com.example.socialmosaiccore.exception.RequestNotFoundException
import com.example.socialmosaiccore.model.ProcessStatus
import com.example.socialmosaiccore.repository.ProcessResultRepository
import jakarta.persistence.EntityNotFoundException
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
        return try {
            processResultRepository.getReferenceById(requestId)
        } catch (e: EntityNotFoundException) {
            throw ProcessResultNotFoundException(
                    requestId = requestId,
            )
        }
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