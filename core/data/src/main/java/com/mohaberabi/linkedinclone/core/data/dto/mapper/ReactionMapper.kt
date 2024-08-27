package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.core.data.dto.ReactionDto


fun ReactionDto.toReactionModel() = ReactionModel(
    postId = postId,
    reactionType = ReactionType.valueOf(reactionType),
    reactorId = reactorId,
    reactorBio = reactorBio,
    reactorImg = reactorImg,
    createdAtMillis = createdAtMillis

)

fun ReactionModel.toReactionDto() = ReactionDto(
    postId = postId,
    reactionType = reactionType.name,
    reactorId = reactorId,
    reactorBio = reactorBio,
    reactorImg = reactorImg,
    createdAtMillis = createdAtMillis
)