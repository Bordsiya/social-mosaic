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
}