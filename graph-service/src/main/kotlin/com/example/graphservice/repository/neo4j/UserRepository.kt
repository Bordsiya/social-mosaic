package com.example.graphservice.repository.neo4j

import com.example.graphservice.entity.neo4j.UserNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : Neo4jRepository<UserNode, String> {
}