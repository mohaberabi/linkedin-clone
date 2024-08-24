package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedinclone.core.data.dto.UserDto


fun UserModel.toUserDto() = UserDto(
    uid = uid,
    name = name,
    email = email,
    lastname = lastname,
    cover = cover,
    bio = bio,
    img = img
)

fun UserDto.toUserModel() = UserModel(
    uid = uid,
    name = name,
    email = email,
    lastname = lastname,
    cover = cover,
    bio = bio,
    img = img
)