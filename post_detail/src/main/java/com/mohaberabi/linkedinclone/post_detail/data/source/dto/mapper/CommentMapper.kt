package com.mohaberabi.linkedinclone.post_detail.data.source.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedinclone.post_detail.data.source.dto.CommentDto


fun CommentDto.toCommentModel() = PostCommentModel(
    id,
    commenterId,
    commenterBio,
    commentedAtMillis,
    commentImg,
    comment,
    postId
)

fun PostCommentModel.toCommentDto() = CommentDto(
    id,
    commenterId,
    commenterBio,
    commentedAtMillis,
    commentImg,
    comment,
    postId
)