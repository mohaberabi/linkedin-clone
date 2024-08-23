package com.mohaberabi.presentation.ui.util

import android.content.Context
import androidx.annotation.StringRes
import com.mohaberabi.core.presentation.design_system.R
import com.mohaberabi.linkedin.core.domain.error.AppError
import com.mohaberabi.linkedin.core.domain.error.CommonError
import com.mohaberabi.linkedin.core.domain.error.LocalError
import com.mohaberabi.linkedin.core.domain.error.RemoteError


sealed class UiText {

    data object Empty : UiText()

    data class Dynamic(val value: String) : UiText()

    data class StringResources(
        @StringRes val id: Int,
        val formatArgs: List<Any> = listOf()
    ) : UiText()


    fun asString(
        context: Context,
    ): String {
        return when (this) {
            is Dynamic -> this.value
            Empty -> ""
            is StringResources -> context.getString(this.id, this.formatArgs.toTypedArray())
        }
    }


    val isEmpty: Boolean
        get() = this == Empty
}


fun AppError.asUiText(): UiText {
    val id = when (this) {

        is CommonError -> {
            when (this) {
                CommonError.IO_ERROR -> R.string.unknown_error
                CommonError.UNKNOWN -> R.string.unknown_error
            }
        }

        is RemoteError -> {
            when (this) {
                RemoteError.REQUEST_TIMEOUT -> R.string.request_timeout
                RemoteError.UNAUTHORIZED -> R.string.unAuthed
                RemoteError.CONFLICT -> R.string.conflict
                RemoteError.TOO_MANY_REQUEST -> R.string.too_many_request
                RemoteError.NO_NETWORK -> R.string.no_netowrk
                RemoteError.PAYLOAD_TOO_LARGE -> R.string.payload_too_large
                RemoteError.SERVER_ERROR -> R.string.server_error
                RemoteError.SERIALIZATION_ERROR -> R.string.serialize_error
                RemoteError.UNKNOWN_ERROR -> R.string.unknown_error
                RemoteError.WRONG_EMAIL_USERNAME -> R.string.invalid_email
                RemoteError.WRONG_PASSWORD -> R.string.invalid_password
                RemoteError.INVALID_CREDENTIALS -> R.string.invalid_email
                RemoteError.INVALID_USER -> R.string.invalid_email
                RemoteError.EMAIL_ALREADY_IN_USE -> R.string.invalid_email
                RemoteError.USER_DISABLED -> R.string.invalid_email
            }
        }

        is LocalError -> {
            when (this) {
                LocalError.DISK_FULL -> R.string.disk_full
                LocalError.UNKNOWN -> R.string.unknown_error
                LocalError.IO -> R.string.disk_full
                LocalError.DATA_CORRUPTION -> R.string.unknown_error
                LocalError.ILLEGAL_STATE -> R.string.unknown_error
            }
        }

        else -> R.string.unknown_error
    }
    return UiText.StringResources(id)
}
