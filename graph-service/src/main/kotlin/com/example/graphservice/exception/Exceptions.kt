package com.example.graphservice.exception

import java.lang.RuntimeException

open class ProcessException(
        override val message: String,
        val code: String,
        override val cause: Throwable? = null,
) : RuntimeException(message, cause)

class GetFriendsVkApiException(
        val userId: Long,
        cause: Throwable? = null,
) : ProcessException(
        message = "Get friends for userId=$userId VK API exception",
        code = "GET_FRIENDS_VK_API_ERROR",
        cause = cause,
)

class GetGroupsVkApiException(
        val userId: Long,
        cause: Throwable? = null,
) : ProcessException(
        message = "Get groups for userId=$userId VK API exception",
        code = "GET_GROUPS_VK_API_ERROR",
        cause = cause,
)

class GetProfilePhotosVkApiException(
        val userId: Long,
        cause: Throwable? = null,
) : ProcessException(
        message = "Get profile photos for userId=$userId VK API exception",
        code = "GET_FRIENDS_VK_API_ERROR",
        cause = cause,
)

class FriendsRevisionDraftNotFoundException(
    val userId: String,
    revision: String,
    cause: Throwable? = null,
) : ProcessException(
        message = "Friends revision draft with userId=$userId and revision=$revision not found",
        code = "FRIENDS_REVISION_DRAFT_NOT_FOUND",
        cause = cause,
)

class GroupThemesRevisionDraftNotFoundException(
        revision: String,
        cause: Throwable? = null,
) : ProcessException(
        message = "Group themes revision draft with revision=$revision not found",
        code = "GROUP_THEMES_REVISION_DRAFT_NOT_FOUND",
        cause = cause,
)

class ImgThemesRevisionDraftNotFoundException(
        revision: String,
        cause: Throwable? = null,
) : ProcessException(
        message = "Img themes revision draft with revision=$revision not found",
        code = "IMG_THEMES_REVISION_DRAFT_NOT_FOUND",
        cause = cause,
)

class GroupSimilarityRevisionDraftNotFoundException(
        revision: String,
        cause: Throwable? = null,
) : ProcessException(
        message = "Group similarity revision draft with revision=$revision not found",
        code = "GROUP_SIMILARITY_REVISION_DRAFT_NOT_FOUND",
        cause = cause,
)

class ImgSimilarityRevisionDraftNotFoundException(
        revision: String,
        cause: Throwable? = null,
) : ProcessException(
        message = "Img similarity revision draft with revision=$revision not found",
        code = "IMG_SIMILARITY_REVISION_DRAFT_NOT_FOUND",
        cause = cause,
)

class DraftApplyNotFoundException(
        revision: String,
        cause: Throwable? = null,
) : ProcessException(
        message = "Draft apply with revision=$revision not found",
        code = "DRAFT_APPLY_NOT_FOUND",
        cause = cause,
)