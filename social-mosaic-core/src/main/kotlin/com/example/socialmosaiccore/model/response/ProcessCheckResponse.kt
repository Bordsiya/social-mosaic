package com.example.socialmosaiccore.model.response

import com.example.socialmosaiccore.model.Error
import com.example.socialmosaiccore.model.ProcessStatus
import com.fasterxml.jackson.annotation.JsonProperty

data class ProcessCheckResponse(
        @JsonProperty("status", required = true)
        val processStatus: ProcessStatus,
        @JsonProperty("revision")
        val revision: String? = null,
        @JsonProperty("error")
        val error: Error? = null,
)
