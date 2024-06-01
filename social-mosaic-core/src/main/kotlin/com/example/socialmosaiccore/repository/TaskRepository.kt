package com.example.socialmosaiccore.repository

import com.example.socialmosaiccore.entity.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, String> {
}