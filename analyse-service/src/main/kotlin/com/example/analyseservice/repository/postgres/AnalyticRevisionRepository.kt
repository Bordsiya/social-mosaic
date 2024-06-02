package com.example.analyseservice.repository.postgres

import com.example.analyseservice.entity.postgres.AnalyticRevision
import org.springframework.data.jpa.repository.JpaRepository

interface AnalyticRevisionRepository : JpaRepository<AnalyticRevision, String> {
}