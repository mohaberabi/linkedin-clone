package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.mohaberabi.linkedin.core.domain.model.ProfileViewerModel
import com.mohaberabi.linkedin.core.domain.source.remote.ProfileViewsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.core.data.dto.SubmitProfileViewDto
import com.mohaberabi.linkedinclone.core.data.dto.UserDto
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseProfileViewsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : ProfileViewsRemoteDataSource {
    override suspend fun viewSomeoneProfile(
        viewerUid: String,
        viewedUid: String
    ) {

        withContext(dispatchers.io) {
            firestore.safeCall {
                val metaDataOperation = userColl(viewedUid)
                    .collection(EndPoints.USER_DATA)
                    .document(EndPoints.USER_META_DATA)
                val viewersOperation = userColl(viewedUid)
                    .collection(EndPoints.PROFILE_VIEWERS)
                    .document(viewerUid)
                val metaDataMap = mapOf(
                    CommonParams.PROFILE_VIEWS to FieldValue.increment(1L),
                )
                val submitView = SubmitProfileViewDto(
                    viewedUid,
                    System.currentTimeMillis()
                )
                runTransaction { txn ->
                    txn.update(metaDataOperation, metaDataMap)
                    txn.set(viewersOperation, submitView)
                }.await()
            }
        }
    }

    override suspend fun getMyProfileViews(
        limit: Int,
        lastDocId: String?,
        uid: String
    ): List<ProfileViewerModel> {

        return withContext(dispatchers.io) {
            firestore.safeCall {
                val submittedViewsIds = paginate<SubmitProfileViewDto>(
                    limit = limit.toLong(),
                    lastDocId = lastDocId,
                    collection = userColl(uid).collection(EndPoints.PROFILE_VIEWERS),
                    orderBy = CommonParams.VIEWED_AT_MILLIS,
                    ascending = false
                ).associateBy { it.uid }

                val filter = Filter.inArray(
                    FieldPath.documentId(),
                    submittedViewsIds.keys.toList(),
                )
                collection(EndPoints.USERS).where(filter).get()
                    .await().mapNotNull {
                        val user = it.toObject<UserDto>()
                        ProfileViewerModel(
                            name = "${user.name} ${user.lastname}",
                            viewedAtMillis = submittedViewsIds[user.uid]!!.viewedAtMillis,
                            uid = user.uid,
                            bio = user.bio,
                            img = user.img
                        )
                    }
            }
        }
    }

    private fun userColl(uid: String) = firestore
        .collection(EndPoints.USERS)
        .document(uid)
}