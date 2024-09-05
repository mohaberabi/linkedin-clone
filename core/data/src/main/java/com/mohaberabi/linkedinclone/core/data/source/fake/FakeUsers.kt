package com.mohaberabi.linkedinclone.core.data.source.fake

import com.mohaberabi.linkedin.core.domain.model.UserModel


object FakeUsers {
    val fakeUsers = buildList {
        repeat(50) {
            add(
                UserModel(
                    email = "",
                    name = names.random(),
                    bio = bios.random(),
                    cover = images.random(),
                    lastname = "",
                    uid = it.toString(),
                    img = images.random()
                )
            )
        }
    }
}