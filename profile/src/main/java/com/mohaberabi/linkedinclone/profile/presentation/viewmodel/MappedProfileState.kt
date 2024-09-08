package com.mohaberabi.linkedinclone.profile.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import com.mohaberabi.linkedin.core.domain.model.UserModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class MappedProfileState(
    private val currentUserFlow: Flow<UserModel?>,
    private val currentUserMetaDataFlow: Flow<UserMetaData>,
    private val otherUserUid: String?,
    private val onLoadOtherUser: (String) -> Unit,
    private val otherUserState: Flow<ProfileState>,
    private val onSetupCurrentUser: () -> Unit,
) {
    private val currentUserMetaDataCombine =
        combine(
            currentUserFlow,
            currentUserMetaDataFlow,
        ) { current, meta ->
            current to meta
        }
    private var didSetupCurrentUser = false
    private var didLoadOtherUser = false

    operator fun invoke() = currentUserFlow
        .flatMapLatest { currentUserState ->
            currentUserState?.let { currentUser ->
                if (currentUser.uid == otherUserUid || otherUserUid == null) {
                    if (!didSetupCurrentUser) {
                        onSetupCurrentUser()
                        didLoadOtherUser = true
                    }
                    currentUserData()
                } else {
                    if (!didLoadOtherUser) {
                        onLoadOtherUser(otherUserUid)
                        didLoadOtherUser = true
                    }
                    otherUserState
                }
            } ?: flowOf(ProfileState(state = ProfileStatus.Error))

        }

    private fun currentUserData() = currentUserMetaDataCombine
        .map { (userState, meta) ->
            ProfileState(
                user = userState,
                profileViews = meta.profileViews,
                state = ProfileStatus.Done,
                isCurrentUser = true
            )
        }

}