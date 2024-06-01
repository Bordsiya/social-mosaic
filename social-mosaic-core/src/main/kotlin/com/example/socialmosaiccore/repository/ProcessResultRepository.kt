package com.example.socialmosaiccore.repository

import com.example.socialmosaiccore.entity.ProcessResult
import org.springframework.data.jpa.repository.JpaRepository

interface ProcessResultRepository : JpaRepository<ProcessResult, String> {
}