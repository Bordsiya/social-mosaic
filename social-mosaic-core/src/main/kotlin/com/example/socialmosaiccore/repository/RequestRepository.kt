package com.example.socialmosaiccore.repository

import com.example.socialmosaiccore.entity.Request
import org.springframework.data.jpa.repository.JpaRepository

interface RequestRepository : JpaRepository<Request, String> {
    fun findByIdempotencyToken(idempotencyToken: String): Request?
    fun findByParentTask(parentTask: String): Request?
}