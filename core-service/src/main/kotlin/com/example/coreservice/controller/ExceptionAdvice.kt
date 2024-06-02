package com.example.coreservice.controller

import com.example.coreservice.model.Error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionAdvice {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: WebRequest): ResponseEntity<com.example.coreservice.model.Error> {
        return ResponseEntity.internalServerError().body(
                Error(
                        code = HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
                        message = e.message ?: "Unexpected error"
                )
        )
    }
}