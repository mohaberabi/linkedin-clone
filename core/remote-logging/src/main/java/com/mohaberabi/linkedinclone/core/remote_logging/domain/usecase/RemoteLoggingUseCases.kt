package com.mohaberabi.linkedinclone.core.remote_logging.domain.usecase


data class RemoteLoggingUseCases(
    val log: RemoteLogUseCase,
    val recordException: RecordExceptionUseCase
)