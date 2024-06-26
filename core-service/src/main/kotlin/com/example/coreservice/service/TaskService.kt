package com.example.coreservice.service

import com.example.coreservice.entity.Task
import com.example.coreservice.exception.RequestNotFoundException
import com.example.coreservice.model.TaskStatus
import com.example.coreservice.repository.TaskRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class TaskService(
        val taskRepository: TaskRepository,
        val requestService: RequestService,
) {

    @Throws(
            RequestNotFoundException::class,
    )
    fun save(
            parentId: String,
            name: String,
            status: TaskStatus,
            error: String? = null,
            errorDescription: String? = null,
            createdDate: Instant,
    ): Task {
        val request = requestService.getByParentId(parentId)
        return taskRepository.save(
                Task(
                        id = UUID.randomUUID().toString(),
                        parentId = request,
                        name = name,
                        status = status,
                        error = error,
                        errorDescription = errorDescription,
                        createdDate = createdDate,
                )
        )
    }
}