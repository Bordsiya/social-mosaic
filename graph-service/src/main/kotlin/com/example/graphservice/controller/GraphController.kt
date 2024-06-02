package com.example.graphservice.controller

import com.example.graphservice.api.GraphApi
import com.example.graphservice.model.GraphType
import com.example.graphservice.model.request.*
import com.example.graphservice.model.response.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class GraphController(

): GraphApi {
    override fun graphSettingsGet(graphRevision: String): ResponseEntity<GraphSettingsResponse> {
        TODO("Not yet implemented")
    }

    override fun graphRevisionGet(userId: String): ResponseEntity<GraphRevisionResponse> {
        TODO("Not yet implemented")
    }

    override fun imgThemesUpdatePost(imgThemesUpdateRequest: ImgThemesUpdateRequest): ResponseEntity<ImgThemesRevisionResponse> {
        TODO("Not yet implemented")
    }

    override fun groupThemesUpdatePost(groupThemesUpdateRequest: GroupThemesUpdateRequest): ResponseEntity<GroupThemesRevisionResponse> {
        TODO("Not yet implemented")
    }

    override fun friendsUpdatePost(friendsUpdateRequest: FriendsUpdateRequest): ResponseEntity<FriendsRevisionResponse> {
        TODO("Not yet implemented")
    }

    override fun imgSimilarityUpdatePost(imgSimilarityUpdateRequest: ImgSimilarityUpdateRequest): ResponseEntity<ImgSimilarityRevisionResponse> {
        TODO("Not yet implemented")
    }

    override fun groupSimilarityUpdatePost(groupSimilarityUpdateRequest: GroupSimilarityUpdateRequest): ResponseEntity<GroupSimilarityRevisionResponse> {
        TODO("Not yet implemented")
    }

    override fun draftApplyPost(draftApplyRequest: DraftApplyRequest): ResponseEntity<GraphRevisionResponse> {
        TODO("Not yet implemented")
    }

    override fun graphGet(graphRevision: String, graphType: GraphType): ResponseEntity<GraphResponse> {
        TODO("Not yet implemented")
    }
}