package com.example.graphservice.repository

import com.example.graphservice.entity.DraftApply
import org.springframework.data.jpa.repository.JpaRepository

interface DraftApplyRepository : JpaRepository<DraftApply, String> {
}