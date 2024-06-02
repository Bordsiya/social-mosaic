package com.example.updateservice.model

data class UpdateTaskResponse(
        val requestId: String,
        val parentId: String,
        val taskName: String,
        val taskStatus: TaskStatus,
        val revision: String? = null,
        val error: String? = null,
        val errorDescription: String? = null,
)
