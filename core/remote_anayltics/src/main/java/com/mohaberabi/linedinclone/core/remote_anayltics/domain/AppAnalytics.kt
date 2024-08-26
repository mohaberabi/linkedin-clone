package com.mohaberabi.linedinclone.core.remote_anayltics.domain


interface AppAnalytics {


    fun logEvent(name: String, params: Map<String, String> = mapOf())

    fun setUserId(id: String)
}
