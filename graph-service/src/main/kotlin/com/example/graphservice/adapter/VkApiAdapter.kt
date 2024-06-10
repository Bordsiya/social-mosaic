package com.example.graphservice.adapter

import com.example.graphservice.exception.GetFriendsVkApiException
import com.example.graphservice.exception.GetGroupsVkApiException
import com.example.graphservice.exception.GetProfilePhotosVkApiException
import com.example.graphservice.wrapper.VKApiWrapper
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.objects.groups.Fields
import com.vk.api.sdk.objects.photos.PhotoSizesType
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.net.URI

@Component
class VkApiAdapter(
        private val vkApiWrapper: VKApiWrapper,
) {
    suspend fun getUserFriendsIds(
            userActor: UserActor,
            friendsAmount: Int,
    ): List<Long> {
        val vkClient = vkApiWrapper.vkApiClient()
        val response = try {
            vkClient.friends()
                    .get(userActor)
                    .count(friendsAmount)
                    .execute()
        } catch (e: Exception) {
            logger.info {
                "Get user friends ids for userId=${userActor.id} exception, ${e.message}"
            }
            throw GetFriendsVkApiException(userId = userActor.id)
        }
        return response.items
    }

    suspend fun getUserGroupsThematics(
            userActor: UserActor,
            groupsAmount: Int,
    ): List<String> {
        val vkClient = vkApiWrapper.vkApiClient()
        val response = try {
            vkClient.groups()
                    .getObjectExtended(userActor)
                    .extended(true)
                    .fields(Fields.ACTIVITY)
                    .count(groupsAmount)
                    .execute()
        } catch (e: Exception) {
            logger.info {
                "Get user groups themes for userId=${userActor.id} exception, ${e.message}"
            }
            throw GetGroupsVkApiException(userId = userActor.id)
        }
        return response.items.map { it.activity }
    }

    suspend fun getUserProfilePhotos(
            userActor: UserActor,
            photosAmount: Int,
    ): List<URI> {
        val vkClient = vkApiWrapper.vkApiClient()
        val response = try {
            vkClient.photos()
                    .get(userActor)
                    .albumId("profile")
                    .count(photosAmount)
                    .execute()
        } catch (e: Exception) {
            logger.info {
                "Get user profile photos for userId=${userActor.id} exception, ${e.message}"
            }
            throw GetProfilePhotosVkApiException(userId = userActor.id)
        }
        return response.items?.mapNotNull { photo ->
            photo.sizes?.firstOrNull { it.type == PhotoSizesType.M }?.url
        } ?: listOf()
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}