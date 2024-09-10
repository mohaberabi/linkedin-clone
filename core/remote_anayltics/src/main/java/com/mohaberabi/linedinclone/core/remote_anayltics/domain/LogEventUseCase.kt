package com.mohaberabi.linedinclone.core.remote_anayltics.domain

class LogEventUseCase(
    private val appAnalytics: AppAnalytics
) {


    operator fun invoke(
        name: String, params: Map<String, String> = mapOf(),
    ) =
        appAnalytics.logEvent(name = name, params = params)
}