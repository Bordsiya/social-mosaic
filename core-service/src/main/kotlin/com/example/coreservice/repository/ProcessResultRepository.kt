package com.example.coreservice.repository

import com.example.coreservice.entity.ProcessResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessResultRepository : JpaRepository<ProcessResult, String> {
}