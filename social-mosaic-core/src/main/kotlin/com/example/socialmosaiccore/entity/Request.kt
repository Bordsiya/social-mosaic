package com.example.socialmosaiccore.entity

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "request")
data class Request(
        @Id
        @Column(name = "id")
        @JsonView
        val id: String? = null,

        @Column(name = "created_date", nullable = false)
        @JsonView
        val createdDate: Instant? = null,

        @Column(name = "input", nullable = false)
        @JsonView
        val input: String? = null,

        @Column(name = "modified_date", nullable = true)
        @JsonView
        val modifiedDate: Instant? = null,

        @Column(name = "open", nullable = false)
        @JsonView
        val status: Char? = null,

        @Column(name = "type", nullable = false)
        @JsonView
        val type: String? = null,

        @Column(name = "idempotency_token", nullable = false)
        @JsonView
        val idempotencyToken: String? = null,

        @Column(name = "parent_task", nullable = true)
        @JsonView
        val parentTask: String? = null,

        @OneToOne(mappedBy = "requestId", cascade = [CascadeType.ALL], orphanRemoval = true)
        val processResult: ProcessResult? = null,

        @OneToMany(mappedBy = "parentId", cascade = [CascadeType.ALL], orphanRemoval = true)
        val tasks: List<Task> = listOf(),
)
