package com.mohaberabi.linkedinclone.post_detail.data.source.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedinclone.post_detail.data.source.dto.CommentDto


fun CommentDto.toCommentModel() = PostCommentModel(
    id = id,
    commenterId = commenterId,
    commenterBio = commenterBio,
    commentedAtMillis = commentedAtMillis,
    commentImg = commentImg,
    comment = comment,
    postId = postId,
    commentorName = commentorName
)

fun PostCommentModel.toCommentDto() = CommentDto(
    id = id,
    commenterId = commenterId,
    commenterBio = commenterBio,
    commentedAtMillis = commentedAtMillis,
    commentImg = commentImg,
    comment = comment,
    postId = postId,
    commentorName = commentorName
)