package com.example.socialmosaiccore.service

import com.example.socialmosaiccore.entity.Request
import com.example.socialmosaiccore.exception.IdempotencyTokenViolationException
import com.example.socialmosaiccore.exception.RequestNotFoundById
import com.example.socialmosaiccore.exception.RequestNotFoundByParentId
import com.example.socialmosaiccore.exception.RequestNotFoundException
import com.example.socialmosaiccore.repository.RequestRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RequestService(
        private val requestRepository: RequestRepository,
) {

    @Throws(
            RequestNotFoundException::class,
    )
    fun get(requestId: String): Request {
        return try {
            requestRepository.getReferenceById(requestId)
        } catch (e: EntityNotFoundException) {
            throw RequestNotFoundById(requestId)
        }
    }

    @Throws(
            RequestNotFoundException::class,
    )
    fun getByParentId(parentId: String): Request {
        return requestRepository.findByParentTask(parentId) ?: throw RequestNotFoundByParentId(parentId)
    }

    @Throws(
            IdempotencyTokenViolationException::class,
    )
    fun save(request: Request): RequestSaveResult {
        val existingRequest = requestRepository.findByIdempotencyToken(request.idempotencyToken!!)
                ?: return RequestSaveResult(
                        request = requestRepository.save(request),
                        isExisted = false,
                )
        if (request.type == existingRequest.type) {
            return RequestSaveResult(
                    request = existingRequest,
                    isExisted = true,
            )
        } else {
            throw IdempotencyTokenViolationException(
                    requestId = request.id!!,
                    idempotencyToken = request.idempotencyToken,
            )
        }
    }

    @Throws(
            RequestNotFoundException::class,
    )
    fun update(requestId: String, status: Char? = null, modifiedDate: Instant? = null): Request {
        val existedRequest = get(requestId)
        val newStatus = status ?: existedRequest.status
        val newModifiedDate = modifiedDate ?: existedRequest.modifiedDate
        return requestRepository.save(
                existedRequest.copy(
                        status = newStatus,
                        modifiedDate = newModifiedDate,
                )
        )
    }

    data class RequestSaveResult(
            val request: Request,
            val isExisted: Boolean,
    )
}