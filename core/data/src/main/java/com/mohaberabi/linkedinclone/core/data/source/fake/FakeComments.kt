package com.mohaberabi.linkedinclone.core.data.source.fake


import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedinclone.core.data.source.fake.FakePosts.fakePostPrefix
import kotlin.random.Random


object FakeComments {
    private val commentsData = listOf(
        "Great post! Totally agree with you.",
        "I’ve been struggling with this too. Thanks for sharing!",
        "This is super helpful. I’ll definitely try this out.",
        "I didn’t know about this. Thanks for the info!",
        "This was a great read. Thanks for sharing your knowledge.",
        "I’ve had a similar experience. Thanks for the advice!",
        "Awesome tips, really helped me out.",
        "This is something I’ve been looking for, thanks!",
        "Your posts are always so informative. Thanks!",
        "I’m going to apply this in my current project. Thanks!"
    )

    private fun generateRandomPostCommentModel(
        postId: String,
        commentorId: String,
    ): PostCommentModel {
        return PostCommentModel(
            id = Random.nextLong().toString(),
            commenterId = commentorId,
            commenterBio = bios.random(),
            commentedAtMillis = System.currentTimeMillis() - Random.nextLong(0, 1000000000),
            commentImg = images.random(),
            comment = commentsData.random(),
            postId = postId,
            commentorName = names.random(),
        )
    }

    val fakeComments = buildList {
        repeat(50) {
            add(
                generateRandomPostCommentModel(
                    postId = fakePostPrefix + it,
                    commentorId = "FakeCommenterId$it"
                )
            )
        }
    }
}
