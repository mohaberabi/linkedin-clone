package com.mohaberabi.linedinclone.core.remote_anayltics.domain


private object AppAnalyticsConst {
    const val SCREEN_KEY_OPEN = "screenOpen"
    const val SCREEN_KEY_CLOSED = "screenClosed"
    const val SCREEN_NAME_KEY = "screenName"
    fun screenParams(name: String) = mapOf(SCREEN_NAME_KEY to name)
}

interface AppAnalytics {


    fun logEvent(name: String, params: Map<String, String> = mapOf())

    fun setUserId(id: String)
}


fun AppAnalytics.screenOpened(name: String) =
    logEvent(
        name = AppAnalyticsConst.SCREEN_KEY_OPEN,
        params = AppAnalyticsConst.screenParams(name = name)
    )

fun AppAnalytics.screenClosed(name: String) =
    logEvent(
        name = AppAnalyticsConst.SCREEN_KEY_CLOSED,
        params = AppAnalyticsConst.screenParams(name = name)
    )