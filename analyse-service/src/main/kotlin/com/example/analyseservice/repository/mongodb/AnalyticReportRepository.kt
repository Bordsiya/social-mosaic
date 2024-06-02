package com.example.analyseservice.repository.mongodb

import com.example.analyseservice.entity.mongodb.AnalyticReport
import org.springframework.data.mongodb.repository.MongoRepository

interface AnalyticReportRepository : MongoRepository<AnalyticReport, String> {
}