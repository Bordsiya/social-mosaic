package com.example.coreservice.repository

import com.example.coreservice.entity.Request
import org.springframework.data.jpa.repository.JpaRepository

interface RequestRepository : JpaRepository<Request, String> {
    fun findByIdempotencyToken(idempotencyToken: String): Request?
    fun findByParentTask(parentTask: String): Request?
}