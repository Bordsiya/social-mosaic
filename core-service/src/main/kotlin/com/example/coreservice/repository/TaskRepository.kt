package com.example.coreservice.repository

import com.example.coreservice.entity.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, String> {
}