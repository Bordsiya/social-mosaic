package com.example.socialmosaiccore.exception

import java.lang.RuntimeException

open class ProcessException(
        override val message: String,
        val code: String,
        override val cause: Throwable? = null,
) : RuntimeException(message, cause)

class IdempotencyTokenViolationException(
        requestId: String,
        idempotencyToken: String,
        cause: Throwable? = null,
) : ProcessException(
        message = "Request with idempotencyToken=$idempotencyToken already exists, requestId=$requestId",
        code = "IDEMPOTENCY_TOKEN_VIOLATION",
        cause = cause,
)

open class RequestNotFoundException(
        field: String,
        fieldValue: String,
        cause: Throwable? = null,
) : ProcessException(
        message = "Request with $field=$fieldValue not found",
        code = "REQUEST_NOT_FOUND",
        cause = cause,
)

class RequestNotFoundById(
        fieldValue: String,
        cause: Throwable? = null,
): RequestNotFoundException(
        field = "id",
        fieldValue = fieldValue,
        cause = cause,
)

class RequestNotFoundByParentId(
        fieldValue: String,
        cause: Throwable? = null,
): RequestNotFoundException(
        field = "parent_id",
        fieldValue = fieldValue,
        cause = cause,
)

class ProcessResultNotFoundException(
        requestId: String,
        cause: Throwable? = null,
) : ProcessException(
        message = "Process result of request_id=$requestId not found",
        code = "PROCESS_RESULT_NOT_FOUND",
        cause = cause,
)