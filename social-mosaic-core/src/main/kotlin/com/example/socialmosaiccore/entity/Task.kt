package com.example.socialmosaiccore.entity

import com.example.socialmosaiccore.model.TaskStatus
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "request")
data class Task(
        @Id
        @Column(name = "id")
        @JsonView
        val id: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_task", nullable = false)
        @JsonView
        val parentId: Request? = null,

        @Column(name = "name", nullable = false)
        @JsonView
        val name: String? = null,

        @Column(name = "status", nullable = false)
        @JsonView
        val status: TaskStatus? = null,

        @Column(name = "error", nullable = true)
        @JsonView
        val error: String? = null,

        @Column(name = "error_description", nullable = true)
        @JsonView
        val errorDescription: String? = null,

        @Column(name = "created_date", nullable = false)
        @JsonView
        val createdDate: Instant? = null,
)
