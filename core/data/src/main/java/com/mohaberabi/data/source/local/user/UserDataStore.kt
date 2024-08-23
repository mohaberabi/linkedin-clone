package com.mohaberabi.data.source.local.user

import com.mohaberabi.data.dto.UserDto
import com.mohaberabi.data.dto.mapper.toUserDto
import com.mohaberabi.data.dto.mapper.toUserModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.source.local.PersistenceClient
import com.mohaberabi.linkedin.core.domain.source.local.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val persistenceClient: PersistenceClient,
) : UserLocalDataSource {

    companion object {
        private const val USER_KEY = "userKey"
    }

    override suspend fun saveUser(
        user: UserModel,
    ) {
        val serial = Json.encodeToString(user.toUserDto())
        persistenceClient.write(USER_KEY, serial)
    }

    override suspend fun delete() {
        persistenceClient.delete(USER_KEY)
    }

    override fun getUser(): Flow<UserModel?> {
        return persistenceClient.read(USER_KEY).map { value ->
            value?.let {
                Json.decodeFromString<UserDto>(it).toUserModel()
            }
        }
    }
}