package com.example.coreservice.model.response

import com.example.coreservice.model.Error
import com.example.coreservice.model.ProcessStatus
import com.fasterxml.jackson.annotation.JsonProperty

data class ProcessCheckResponse(
        @JsonProperty("status", required = true)
        val processStatus: ProcessStatus,
        @JsonProperty("revision")
        val revision: String? = null,
        @JsonProperty("error")
        val error: Error? = null,
)
