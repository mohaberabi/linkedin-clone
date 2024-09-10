package com.mohaberabi.linkedinclone.core.remote_logging.domain.usecase

import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.RemoteLogging

class RecordExceptionUseCase(
    private val remoteLogging: RemoteLogging
) {

    operator fun invoke(e: Throwable? = null) = remoteLogging.recordException(e)
}