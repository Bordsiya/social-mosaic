package com.example.socialmosaiccore.model.response

import com.example.socialmosaiccore.model.TaskStatus

data class TaskResponse(
        val requestId: String,
        val parentId: String,
        val taskName: String,
        val taskStatus: TaskStatus,
        val revision: String? = null,
        val error: String? = null,
        val errorDescription: String? = null,
)
