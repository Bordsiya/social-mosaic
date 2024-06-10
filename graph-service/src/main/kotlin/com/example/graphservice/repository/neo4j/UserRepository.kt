package com.example.graphservice.repository.neo4j

import com.example.graphservice.entity.neo4j.UserNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : Neo4jRepository<UserNode, String> {
    fun findByUserId(userId: String): UserNode?

    @Query("MATCH (u1:User)-[r:FRIENDS_SIMILARITY] -> (u2:User) " +
            "WHERE u1.userId = \$user1Id AND u2.userId = \$user2Id AND r.friends_revision = \$revision " +
            "DELETE r")
    fun deleteFriendshipByUserIdsAndRevision(user1Id: String, user2Id: String, revision: String)

    @Query(" MATCH (user:User {userId: \$userId})" +
        "MATCH (user)-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User)" +
        "RETURN user, fs, friend")
    fun findUserFriendsSimilarityGraph(userId: String, friendsRevision: String): List<UserNode>

    @Query("MATCH (user:User)-[r:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User {user_id: \$userId})" +
        "SET friend.img_thematic_revision = \$imgThematicRevision, friend.img_themes = \$imgThemes" +
        "RETURN friend")
    fun updateImageThemes(userId: String, friendsRevision: String, imgThematicRevision: String, imgThemes: String): List<UserNode>

    @Query("MATCH (user:User)-[r:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User {user_id: \$userId})" +
        "REMOVE friend.img_thematic_revision, friend.img_themes" +
        "RETURN friend")
    fun removeImageThemes(userId: String, friendsRevision: String): List<UserNode>

    @Query("MATCH (user:User)-[r:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User {user_id: \$userId})" +
            "SET friend.group_thematic_revision = \$groupThematicRevision, friend.group_themes = \$groupThemes" +
            "RETURN friend")
    fun updateGroupThemes(userId: String, friendsRevision: String, groupThematicRevision: String, groupThemes: String): List<UserNode>

    @Query("MATCH (user:User)-[r:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User {user_id: \$userId})" +
            "REMOVE friend.group_thematic_revision, friend.group_themes" +
            "RETURN friend")
    fun removeGroupThemes(userId: String, friendsRevision: String): List<UserNode>

    @Query("MATCH (user:User)-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User)" +
    "WHERE user.userId = \$userId AND user.imgThematicRevision = \$imgThematicRevision" +
    "AND friend.userId = \$userId AND friend.imgThematicRevision = \$imgThematicRevision" +
    "MERGE (user)-[is:IMG_SIMILARITY {img_revision: \$imgRevision}]->(friend)" +
    "RETURN friend")
    fun addImgSimilarity(
            userId: String,
            friendsRevision: String,
            imgThematicRevision: String,
            imgRevision: String
    ): List<UserNode>

    @Query("MATCH (user:User)-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User)" +
    "WHERE user.userId = \$userId AND user.imgThematicRevision = \$imgThematicRevision AND" +
          "friend.userId <> \$userId AND friend.imgThematicRevision = \$imgThematicRevision" +
    "MATCH (user)-[is:IMG_SIMILARITY {img_revision: \$imgRevision}]->(friend)" +
    "DELETE is" +
    "RETURN friend")
    fun removeImgSimilarity(
            userId: String,
            friendsRevision: String,
            imgThematicRevision: String,
            imgRevision: String
    ): List<UserNode>

    @Query("MATCH (user:User)-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User)" +
            "WHERE user.userId = \$userId AND user.groupThematicRevision = \$groupThematicRevision" +
            "AND friend.userId = \$userId AND friend.groupThematicRevision = \$groupThematicRevision" +
            "MERGE (user)-[is:GROUP_SIMILARITY {group_revision: \$groupRevision}]->(friend)" +
            "RETURN friend")
    fun addGroupSimilarity(
            userId: String,
            friendsRevision: String,
            groupThematicRevision: String,
            groupRevision: String
    ): List<UserNode>

    @Query("MATCH (user:User)-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User)" +
            "WHERE user.userId = \$userId AND user.groupThematicRevision = \$groupThematicRevision AND" +
            "friend.userId <> \$userId AND friend.groupThematicRevision = \$groupThematicRevision" +
            "MATCH (user)-[is:GROUP_SIMILARITY {group_revision: \$groupRevision}]->(friend)" +
            "DELETE is" +
            "RETURN friend")
    fun removeGroupSimilarity(
            userId: String,
            friendsRevision: String,
            groupThematicRevision: String,
            groupRevision: String
    ): List<UserNode>
}