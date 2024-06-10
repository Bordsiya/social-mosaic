package com.example.coreservice.entity

import com.example.coreservice.model.ProcessStatus
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*

@Entity
@Table(name = "process_result")
data class ProcessResult(
        @Id
        @Column(name = "id")
        @JsonView
        val id: String? = null,

        @OneToOne
        @JoinColumn(name = "request_id", nullable = false)
        @JsonView
        val requestId: Request? = null,

        @Column(name = "revision", nullable = true)
        @JsonView
        val revision: String? = null,

        @Column(name = "process_status", nullable = false)
        @JsonView
        val processStatus: ProcessStatus? = null,

        @Column(name = "error_code", nullable = true)
        @JsonView
        val errorCode: String? = null,

        @Column(name = "error_message", nullable = true)
        @JsonView
        val errorMessage: String? = null,
)
