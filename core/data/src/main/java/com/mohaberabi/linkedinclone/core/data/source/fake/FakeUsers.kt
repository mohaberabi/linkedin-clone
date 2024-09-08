package com.mohaberabi.linkedinclone.core.data.source.fake

import com.mohaberabi.linkedin.core.domain.model.ProfileViewerModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import kotlin.random.Random


object FakeUsers {
    val fakeUsers = buildList {
        repeat(50) {
            add(
                UserModel(
                    email = "",
                    connections = Random.nextInt(),
                    name = names.random(),
                    bio = bios.random(),
                    cover = images.random(),
                    lastname = "",
                    uid = "user$it",
                    img = images.random()
                )
            )
        }
    }
    val fakeViewers = buildList {
        repeat(50) {
            add(
                ProfileViewerModel(
                    name = names.random(),
                    bio = bios.random(),
                    uid = "user$it",
                    img = images.random(),
                    viewedAtMillis = System.currentTimeMillis() - Random.nextLong()
                )
            )
        }
    }
}