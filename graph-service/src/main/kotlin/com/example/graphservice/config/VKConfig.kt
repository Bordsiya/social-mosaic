package com.example.graphservice.config

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class VKConfig {
    @Bean
    fun vkApiClient(): VkApiClient {
        return VkApiClient(HttpTransportClient.getInstance())
    }
}