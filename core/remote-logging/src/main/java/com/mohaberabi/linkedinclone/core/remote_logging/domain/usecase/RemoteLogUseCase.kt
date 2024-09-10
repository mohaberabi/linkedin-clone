package com.mohaberabi.linkedinclone.core.remote_logging.domain.usecase

import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.LogInfo
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.RemoteLogging

class RemoteLogUseCase(
    private val remoteLogging: RemoteLogging,
) {

    operator fun invoke(info: LogInfo) = remoteLogging.log(info)
}