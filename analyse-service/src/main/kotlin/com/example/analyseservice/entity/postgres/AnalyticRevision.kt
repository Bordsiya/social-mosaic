package com.example.analyseservice.entity.postgres

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "analytic_revision")
data class AnalyticRevision(
        @Id
        @Column(name = "id")
        @JsonView
        val id: String? = null,
        @Column(name = "revision_id", nullable = false)
        @JsonView
        val revisionId: String? = null,
        @Column(name = "created_date", nullable = false)
        @JsonView
        val createdDate: Instant? = null,
        @Column(name = "settings", nullable = false)
        @JsonView
        val settings: String? = null,
)
