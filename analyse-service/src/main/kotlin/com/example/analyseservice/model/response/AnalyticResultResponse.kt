package com.example.analyseservice.model.response

import com.example.analyseservice.model.CentralityMeasure
import com.example.analyseservice.model.FriendRecommendation
import com.example.analyseservice.model.GroupRecommendation
import com.example.analyseservice.model.ImageRecommendation
import com.fasterxml.jackson.annotation.JsonProperty

data class AnalyticResultResponse(
        @JsonProperty("group_recommendations", required = true)
        val groupRecommendations: List<GroupRecommendation>,
        @JsonProperty("image_recommendations", required = true)
        val imageRecommendations: List<ImageRecommendation>,
        @JsonProperty("friends_recommendations", required = true)
        val friendRecommendations: List<FriendRecommendation>,
        @JsonProperty("centrality_measures", required = true)
        val centralityMeasures: List<CentralityMeasure>,
)
