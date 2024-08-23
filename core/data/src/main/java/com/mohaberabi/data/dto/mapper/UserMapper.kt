package com.mohaberabi.data.dto.mapper

import com.mohaberabi.data.dto.UserDto
import com.mohaberabi.linkedin.core.domain.model.UserModel


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