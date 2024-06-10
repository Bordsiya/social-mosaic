package com.example.graphservice.exception

import java.lang.RuntimeException

open class VkApiException(
        override val message: String,
        val code: String,
        override val cause: Throwable? = null,
) : RuntimeException(message, cause)

class GetFriendsVkApiException(
        val userId: Long,
        cause: Throwable? = null,
) : VkApiException(
        message = "Get friends for userId=$userId VK API exception",
        code = "GET_FRIENDS_VK_API_ERROR",
        cause = cause,
)

class GetGroupsVkApiException(
        val userId: Long,
        cause: Throwable? = null,
) : VkApiException(
        message = "Get groups for userId=$userId VK API exception",
        code = "GET_GROUPS_VK_API_ERROR",
        cause = cause,
)

class GetProfilePhotosVkApiException(
        val userId: Long,
        cause: Throwable? = null,
) : VkApiException(
        message = "Get profile photos for userId=$userId VK API exception",
        code = "GET_FRIENDS_VK_API_ERROR",
        cause = cause,
)