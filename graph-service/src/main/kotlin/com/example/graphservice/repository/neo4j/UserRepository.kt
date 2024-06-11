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

    @Query("MATCH (user:User {userId: \$userId1, imgThematicRevision: \$imgThematicRevision})-[fs:FRIENDS_SIMILARITY" + "{friends_revision: \$friendsRevision}]->(friend:User {userId: \$userId2, imgThematicRevision: \$imgThematicRevision})" +
            "MERGE (user)-[is:IMG_SIMILARITY]->(friend)" +
            "SET is.img_revision = \$imgRevision" +
            "RETURN friend")
    fun addImgSimilarity(
            userId1: String,
            userId2: String,
            friendsRevision: String,
            imgThematicRevision: String,
            imgRevision: String
    ): List<UserNode>

    @Query("MATCH (user:User {userId: \$userId1, imgThematicRevision: \$imgThematicRevision})-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User {userId: \$userId2, imgThematicRevision: \$imgThematicRevision})-[is:IMG_SIMILARITY {img_revision: \$imgRevision}]->(user)" +
            "DELETE is" +
            "RETURN user, friend")
    fun removeImgSimilarity(
            userId1: String,
            userId2: String,
            friendsRevision: String,
            imgThematicRevision: String,
            imgRevision: String
    ): List<UserNode>

    @Query("MATCH (user:User {userId: \$userId1, groupThematicRevision: \$groupThematicRevision})-[fs:FRIENDS_SIMILARITY" + "{friends_revision: \$friendsRevision}]->(friend:User {userId: \$userId2, groupThematicRevision: \$groupThematicRevision})" +
          "MERGE (user)-[is:GROUP_SIMILARITY]->(friend)" +
          "SET is.group_revision = \$groupRevision" +
          "RETURN friend")
    fun addGroupSimilarity(
            userId1: String,
            userId2: String,
            friendsRevision: String,
            groupThematicRevision: String,
            groupRevision: String
    ): List<UserNode>

    @Query("MATCH (user:User {userId: \$userId1, groupThematicRevision: \$groupThematicRevision})-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User {userId: \$userId2, groupThematicRevision: \$groupThematicRevision})-[is:GROUP_SIMILARITY {group_revision: \$groupRevision}]->(user)" +
            "DELETE is" +
            "RETURN user, friend")
    fun removeGroupSimilarity(
            userId1: String,
            userId2: String,
            friendsRevision: String,
            groupThematicRevision: String,
            groupRevision: String
    ): List<UserNode>

    @Query(" MATCH (user:User {userId: \$userId})" +
            "MATCH (user)-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User {img_thematic_revision: \$imgThematicRevision})" +
            "RETURN user, fs, friend")
    fun findUserFriendsWithImgThematicRevision(
            userId: String,
            friendsRevision: String,
            imgThematicRevision: String
    ): List<UserNode>

    @Query(" MATCH (user:User {userId: \$userId})" +
            "MATCH (user)-[fs:FRIENDS_SIMILARITY {friends_revision: \$friendsRevision}]->(friend:User {group_thematic_revision: \$groupThematicRevision})" +
            "RETURN user, fs, friend")
    fun findUserFriendsWithGroupThematicRevision(
            userId: String,
            friendsRevision: String,
            groupThematicRevision: String
    ): List<UserNode>
}