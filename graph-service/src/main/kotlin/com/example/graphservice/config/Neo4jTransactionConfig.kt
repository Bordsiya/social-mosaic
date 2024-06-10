package com.example.graphservice.config

import org.neo4j.driver.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Configuration
class Neo4jTransactionConfig {
    @Bean
    fun neo4jTransactionManager(driver: Driver): Neo4jTransactionManager {
        return Neo4jTransactionManager(driver)
    }

    @Bean
    fun transactionTemplate(transactionManager: Neo4jTransactionManager): TransactionTemplate {
        return TransactionTemplate(transactionManager)
    }
}