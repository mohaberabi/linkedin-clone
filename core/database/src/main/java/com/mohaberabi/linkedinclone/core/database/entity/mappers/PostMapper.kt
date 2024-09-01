package com.mohaberabi.linkedinclone.core.database.entity.mappers

import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.core.database.entity.PostEntity


fun PostEntity.toPostModel(): PostModel {
    return PostModel(
        id = id,
        createdAtMillis = createdAtMillis,
        issuerName = issuerName,
        issuerUid = issuerUid,
        issuerAvatar = issuerAvatar,
        issuerBio = issuerBio,
        postData = postData,
        postAttachedImg = postAttachedImg,
        commentsCount = commentsCount,
        reactionsCount = reactionsCount,
        repostsCount = repostsCount,
        currentUserReaction = currentUserReaction?.let { ReactionType.valueOf(it) }
    )
}

fun PostModel.toPostEntity(): PostEntity {
    return PostEntity(
        id = id,
        createdAtMillis = createdAtMillis,
        issuerName = issuerName,
        issuerUid = issuerUid,
        issuerAvatar = issuerAvatar,
        issuerBio = issuerBio,
        postData = postData,
        postAttachedImg = postAttachedImg,
        commentsCount = commentsCount,
        reactionsCount = reactionsCount,
        repostsCount = repostsCount,
        currentUserReaction = currentUserReaction?.name
    )
}
