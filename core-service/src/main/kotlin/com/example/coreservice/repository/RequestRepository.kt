package com.example.coreservice.repository

import com.example.coreservice.entity.Request
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository : JpaRepository<Request, String> {
    fun findByIdempotencyToken(idempotencyToken: String): Request?
    fun findByParentTask(parentTask: String): Request?
}