package com.mohaberabi.linkedinclone.posts.data.source.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedinclone.posts.data.source.dto.PostDto


fun PostDto.toPostModel() = PostModel(
    id = id,
    issuerBio = issuerBio,
    issuerUid = issuerUid,
    issuerAvatar = issuerAvatar,
    createdAtMillis = createdAtMillis,
    issuerName = issuerName,
    postAttachedImg = postAttachedImg,
    postData = postData,
    repostsCount = repostsCount,
    commentsCount = commentsCount,
    reactionsCount = reactionsCount,
)