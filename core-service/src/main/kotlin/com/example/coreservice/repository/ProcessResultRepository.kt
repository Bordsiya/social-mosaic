package com.example.coreservice.repository

import com.example.coreservice.entity.ProcessResult
import org.springframework.data.jpa.repository.JpaRepository

interface ProcessResultRepository : JpaRepository<ProcessResult, String> {
}