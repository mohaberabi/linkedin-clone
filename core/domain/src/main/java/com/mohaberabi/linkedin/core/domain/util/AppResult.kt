package com.mohaberabi.linkedin.core.domain.util

import com.mohaberabi.linkedin.core.domain.error.AppError
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.CommonError
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.util.AppResult.Error
import kotlinx.coroutines.CancellationException

typealias EmptyDataResult<E> = AppResult<Unit, E>

sealed interface AppResult<out D, out E : AppError> {
    data class Done<out D>(val data: D) : AppResult<D, Nothing>
    data class Error<out E : AppError>(val error: E) : AppResult<Nothing, E>
    companion object {
        suspend inline fun <D> handleResult(
            call: () -> AppResult<D, ErrorModel>
        ): AppResult<D, ErrorModel> {
            return handleInternal(call)
        }

        inline fun <D> handle(
            call: () -> D
        ): AppResult<D, ErrorModel> {
            return handleInternal {
                Done(call())
            }
        }
    }

}

inline fun <D> handleInternal(
    call: () -> AppResult<D, ErrorModel>
): AppResult<D, ErrorModel> {
    return try {
        call()
    } catch (e: AppException) {
        Error(e.error)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Error(
            ErrorModel(
                message = e.message,
                cause = e,
                type = CommonError.UNKNOWN
            )
        )
    }
}

inline fun <T, E : AppError, R> AppResult<T, E>.map(map: (T) -> R): AppResult<R, E> {
    return when (this) {
        is AppResult.Error -> AppResult.Error(error)
        is AppResult.Done -> AppResult.Done(map(data))
    }
}

fun <T, E : AppError> AppResult<T, E>.asEmptyResult(
): EmptyDataResult<E> {
    return map { }
}

inline fun <T, E : AppError> AppResult<T, E>.onSuccess(
    action: (T) -> Unit,
): AppResult<T, E> {
    if (this is AppResult.Done) {
        action(data)
    }
    return this
}

inline fun <T, E : AppError> AppResult<T, E>.onFailure(
    action: (E) -> Unit,
): AppResult<T, E> {
    if (this is AppResult.Error) {
        action(error)
    }
    return this
}

inline fun <T, E : AppError, R, F : AppError> AppResult<T, E>.foldResult(
    onFailure: (E) -> AppResult<R, F>,
    onSuccess: (T) -> AppResult<R, F>,
): AppResult<R, F> {
    return when (this) {
        is AppResult.Done -> onSuccess(data)
        is AppResult.Error -> onFailure(error)
    }
}
