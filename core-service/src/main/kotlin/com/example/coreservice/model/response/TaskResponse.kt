package com.example.coreservice.model.response

import com.example.coreservice.model.TaskStatus
import com.fasterxml.jackson.annotation.JsonProperty

data class TaskResponse(
        @JsonProperty("request_id", required = true)
        val requestId: String,
        @JsonProperty("parent_id", required = true)
        val parentId: String,
        @JsonProperty("task_name", required = true)
        val taskName: String,
        @JsonProperty("task_status", required = true)
        val taskStatus: TaskStatus,
        @JsonProperty("revision")
        val revision: String? = null,
        @JsonProperty("error")
        val error: String? = null,
        @JsonProperty("error_description")
        val errorDescription: String? = null,
)
