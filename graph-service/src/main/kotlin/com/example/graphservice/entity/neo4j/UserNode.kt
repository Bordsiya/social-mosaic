package com.example.graphservice.entity.neo4j

import jakarta.persistence.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*

@Node("User")
data class UserNode(
        @Id
        val id: String? = UUID.randomUUID().toString(),
        @Property("user_id")
        val userId: String? = null,
        @Property("img_thematic_revision")
        val imgThematicRevision: String? = null,
        @Property("img_themes")
        val imgThemes: String? = null,
        @Property("group_thematic_revision")
        val groupThematicRevision: String? = null,
        @Property("group_themes")
        val groupThemes: String? = null,

        @Relationship(type = "FRIENDS_SIMILARITY", direction = Relationship.Direction.OUTGOING)
        val friendsSimilarities: List<FriendsSimilarity> = emptyList(),

        @Relationship(type = "IMG_SIMILARITY", direction = Relationship.Direction.OUTGOING)
        val imgSimilarities: List<ImgSimilarity> = emptyList(),

        @Relationship(type = "GROUP_SIMILARITY", direction = Relationship.Direction.OUTGOING)
        val groupSimilarities: List<GroupSimilarity> = emptyList()
)
