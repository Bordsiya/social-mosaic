package com.example.graphservice.service

import com.example.graphservice.entity.neo4j.FriendsSimilarity
import com.example.graphservice.entity.neo4j.UserNode
import com.example.graphservice.repository.neo4j.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate

@Service
class GraphService(
        private val transactionTemplate: TransactionTemplate,
        private val userRepository: UserRepository,
) {

    fun ensureUserExists(userId: String): UserNode {
        return transactionTemplate.execute {
            userRepository.findByUserId(userId) ?: userRepository.save(UserNode(userId = userId))
        }!!
    }

    fun addFriendshipIfNotExist(user1Id: String, user2Id: String, revision: String) {
        transactionTemplate.execute {
            val user1 = ensureUserExists(user1Id)
            val user2 = ensureUserExists(user2Id)

            val existingRelation = user1.friendsSimilarities.firstOrNull { it.user == user2 }
            if (existingRelation == null) {
                val newRelation = FriendsSimilarity(user = user2, friendsRevision = revision)
                user1.friendsSimilarities.add(newRelation)
                userRepository.save(user1)
            } else {
                if (existingRelation.friendsRevision != revision) {
                    existingRelation.friendsRevision = revision
                    userRepository.save(user1)
                } else {}
            }
        }
    }

    fun deleteFriendshipByUserIdsAndRevision(user1Id: String, user2Id: String, revision: String) {
        transactionTemplate.execute {
            userRepository.deleteFriendshipByUserIdsAndRevision(user1Id, user2Id, revision)
        }
    }

    fun findUserFriendsSimilarityGraph(userId: String, friendsRevision: String): List<UserNode> {
        return userRepository.findUserFriendsSimilarityGraph(userId, friendsRevision)
    }

    fun updateImageThemes(userId: String, friendsRevision: String, imgThematicRevision: String, imgThemes: String): List<UserNode> {
        return userRepository.updateImageThemes(userId, friendsRevision, imgThematicRevision, imgThemes)
    }

    fun removeImageThemes(userId: String, friendsRevision: String): List<UserNode> {
        return userRepository.removeImageThemes(userId, friendsRevision)
    }

    fun updateGroupThemes(userId: String, friendsRevision: String, groupThematicRevision: String, groupThemes: String): List<UserNode> {
        return userRepository.updateGroupThemes(userId, friendsRevision, groupThematicRevision, groupThemes)
    }

    fun removeGroupThemes(userId: String, friendsRevision: String): List<UserNode> {
        return userRepository.removeGroupThemes(userId, friendsRevision)
    }

    fun updateImgSimilarity(
            userId: String,
            friendsRevision: String,
            imgThematicRevision: String,
            imgRevision: String
    ): List<UserNode> {
        return userRepository.addImgSimilarity(
                userId,
                friendsRevision,
                imgThematicRevision,
                imgRevision,
        )
    }

    fun removeImgSimilarity(
            userId: String,
            friendsRevision: String,
            imgThematicRevision: String,
            imgRevision: String
    ): List<UserNode> {
        return userRepository.removeImgSimilarity(
                userId,
                friendsRevision,
                imgThematicRevision,
                imgRevision,
        )
    }

    fun updateGroupSimilarity(
            userId: String,
            friendsRevision: String,
            groupThematicRevision: String,
            groupRevision: String
    ): List<UserNode> {
        return userRepository.addGroupSimilarity(
                userId,
                friendsRevision,
                groupThematicRevision,
                groupRevision,
        )
    }

    fun removeGroupSimilarity(
            userId: String,
            friendsRevision: String,
            groupThematicRevision: String,
            groupRevision: String
    ): List<UserNode> {
        return userRepository.removeGroupSimilarity(
                userId,
                friendsRevision,
                groupThematicRevision,
                groupRevision,
        )
    }
}