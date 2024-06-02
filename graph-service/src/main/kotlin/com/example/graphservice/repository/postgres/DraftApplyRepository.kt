package com.example.graphservice.repository.postgres

import com.example.graphservice.entity.postgres.DraftApply
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DraftApplyRepository : JpaRepository<DraftApply, String> {
}