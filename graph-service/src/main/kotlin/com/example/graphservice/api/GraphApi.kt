package com.example.graphservice.api

import com.example.graphservice.model.GraphType
import com.example.graphservice.model.request.*
import com.example.graphservice.model.response.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RequestMapping("/")
interface GraphApi {
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/graph/setting"],
            params = ["graph_revision"],
            produces = ["application/json"],
    )
    fun graphGraphSettingsGet(
            @RequestParam("graph_revision") graphRevision: String
    ): ResponseEntity<GraphSettingsResponse>

    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/graph/revision"],
            produces = ["application/json"],
    )
    fun graphRevisionGet(
            @CookieValue(value = "user_id", required = true) userId: String,
    ): ResponseEntity<GraphRevisionResponse>

    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/img/theme/update"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun imgThemesUpdatePost(
            @Valid @RequestBody imgThemesUpdateRequest: ImgThemesUpdateRequest,
    ): ResponseEntity<ImgThemesRevisionResponse>

    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/group/theme/update"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun groupThemesUpdatePost(
            @Valid @RequestBody groupThemesUpdateRequest: GroupThemesUpdateRequest,
    ): ResponseEntity<GroupThemesRevisionResponse>

    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/friend/update"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun friendsUpdatePost(
            @Valid @RequestBody friendsUpdateRequest: FriendsUpdateRequest,
    ): ResponseEntity<FriendsRevisionResponse>

    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/img/theme/similarity"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun imgSimilarityUpdatePost(
            @Valid @RequestBody imgSimilarityUpdateRequest: ImgSimilarityUpdateRequest
    ): ResponseEntity<ImgSimilarityRevisionResponse>

    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/group/theme/similarity"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun groupSimilarityUpdatePost(
            @Valid @RequestBody groupSimilarityUpdateRequest: GroupSimilarityUpdateRequest,
    ): ResponseEntity<GroupSimilarityRevisionResponse>

    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/v1/graph/draft/apply"],
            produces = ["application/json"],
            consumes = ["application/json"],
    )
    fun draftApplyPost(
            @Valid @RequestBody draftApplyRequest: DraftApplyRequest,
    ): ResponseEntity<GraphRevisionResponse>

    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/v1/graph"],
            params = ["graph_revision", "type"],
            produces = ["application/json"],
    )
    fun graphGet(
            @RequestParam("graph_revision") graphRevision: String,
            @RequestParam("type") graphType: GraphType,
    ): ResponseEntity<GraphResponse>
}