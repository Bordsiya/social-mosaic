package com.example.graphservice.entity.neo4j

import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode

@RelationshipProperties
data class FriendsSimilarity(
        @TargetNode
        val user: UserNode,
        @Property("friends_revision")
        val friendsRevision: String? = null,
)

@RelationshipProperties
data class ImgSimilarity(
        @TargetNode
        val user: UserNode,
        @Property("img_revision")
        val imgRevision: String? = null,
)

@RelationshipProperties
data class GroupSimilarity(
        @TargetNode
        val user: UserNode,
        @Property("group_revision")
        val groupRevision: String? = null,
)