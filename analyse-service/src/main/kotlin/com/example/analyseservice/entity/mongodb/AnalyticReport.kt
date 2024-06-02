package com.example.analyseservice.entity.mongodb

import com.example.analyseservice.model.CentralityMeasure
import com.example.analyseservice.model.FriendRecommendation
import com.example.analyseservice.model.GroupRecommendation
import com.example.analyseservice.model.ImageRecommendation
import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "analytic_report")
data class AnalyticReport(
        @Id
        val id: String,
        @Field("group_recommendations")
        val groupRecommendations: List<GroupRecommendation>,
        @Field("img_recommendations")
        val imgRecommendations: List<ImageRecommendation>,
        @Field("friends_recommendations")
        val friendsRecommendations: List<FriendRecommendation>,
        @Field("centrality_measures_top")
        val centralityMeasuresTop: List<CentralityMeasure>
)