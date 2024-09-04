package com.mohaberabi.linkedinclone.core.data.source.local.user

import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.source.local.persistence.PersistenceClient
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedinclone.core.data.dto.UserDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toUserDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toUserModel
import com.mohaberabi.linkedinclone.core.data.util.decode
import com.mohaberabi.linkedinclone.core.data.util.encode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val persistenceClient: PersistenceClient,
) : UserLocalDataSource {

    companion object {
        private const val USER_KEY = "userKey"
    }

    override suspend fun saveUser(
        user: UserModel,
    ) = persistenceClient.write(USER_KEY, user.toUserDto().encode())


    override suspend fun delete() = persistenceClient.delete(USER_KEY)


    override suspend fun getUid(): String =
        persistenceClient.read(USER_KEY).first()!!.decode<UserDto>()!!.uid


    override fun getUser(): Flow<UserModel?> {
        return persistenceClient.read(USER_KEY)
            .map { value ->
                value?.let {
                    it.decode<UserDto>()?.toUserModel()
                }
            }
    }
}