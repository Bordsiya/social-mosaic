package com.example.analyseservice.repository.postgres

import com.example.analyseservice.entity.postgres.AnalyticRevision
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnalyticRevisionRepository : JpaRepository<AnalyticRevision, String> {
}