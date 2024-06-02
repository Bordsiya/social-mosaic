package com.example.graphservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ImgThemesUpdateRequest(
        @JsonProperty("user_id", required = true)
        val userId: String,
        @JsonProperty("friends_revision", required = true)
        val friendsRevision: String,
        @JsonProperty("max_img_themes", required = true)
        val maxImgThemes: Int,
)
