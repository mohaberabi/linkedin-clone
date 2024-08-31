package com.mohaberabi.linkedinclone.core.data.source.fake


import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedinclone.core.data.dto.PostDto
import kotlin.random.Random


object FakePosts {
    const val fakePostPrefix = "FakePost"
    private fun generateRandomPostModel(id: Int): PostDto {
        return PostDto(
            id = "FakePost${id}",
            createdAtMillis = System.currentTimeMillis() - Random.nextLong(0, 1000000000),
            issuerName = names.random(),
            issuerUid = Random.nextInt(1000, 9999).toString(),
            issuerAvatar = images.random(),
            issuerBio = bios.random(),
            postData = postData.random(),
            postAttachedImg = images.random(),
            commentsCount = Random.nextInt(0, 100),
            reactionsCount = Random.nextInt(0, 1000),
            repostsCount = Random.nextInt(0, 100),
        )
    }

    val fakePosts = buildList {
        repeat(50) {
            add(
                generateRandomPostModel(id = it)
            )
        }
    }


}
