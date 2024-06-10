package com.example.graphservice.wrapper

import com.vk.api.sdk.client.VkApiClient
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class VKApiWrapper(
        private val vkApiClient: VkApiClient,
) {
    private var requestCounter: Int = 0
    private var lastRequestTime: Instant = Instant.now()

    @Synchronized
    fun vkApiClient(): VkApiClient {
        val now = Instant.now()

        if (ChronoUnit.SECONDS.between(lastRequestTime, now) < 1) {
            requestCounter++
        } else {
            requestCounter = 1
        }

        if (requestCounter > 2) {
            try {
                val waitTime = 1000 - ChronoUnit.MILLIS.between(lastRequestTime, now)
                Thread.sleep(waitTime)
                requestCounter = 1
            } catch (e: InterruptedException) {
                // reacting to service shutdown and so on - correct thread handling
                Thread.currentThread().interrupt()
                throw IllegalStateException("Thread was interrupted during API rate limit sleep.")
            }
        }

        lastRequestTime = Instant.now()
        return vkApiClient
    }

}