package com.mohaberabi.linkedinclone.core.data.source.fake


import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.core.data.dto.ReactionDto
import com.mohaberabi.linkedinclone.core.data.source.fake.FakePosts.fakePostPrefix
import kotlin.random.Random


object FakeReactions {
    private fun generateRandomReactionModel(postId: String): ReactionDto {
        return ReactionDto(
            postId = postId,
            reactorId = Random.nextInt(1000, 9999).toString(),
            reactorBio = bios.random(),
            reactorImg = images.random(),
            reactionType = ReactionType.entries.random().name,
            createdAtMillis = System.currentTimeMillis() - Random.nextLong(0, 1000000000),
            reactorName = names.random()
        )
    }

    val fakeReactions = buildList {
        repeat(40) {
            add(generateRandomReactionModel("${fakePostPrefix}$it"))
        }
    }


}
