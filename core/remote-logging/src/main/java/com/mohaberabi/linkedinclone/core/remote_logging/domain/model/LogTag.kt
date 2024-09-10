package com.mohaberabi.linkedinclone.core.remote_logging.domain.model


enum class LogTag(val tag: String) {
    ERROR("Error"),
    INFO("Info"),
    STATE_CHANGE("State Changed"),
    USER_ACTION("User Action"),
    CUSTOM_EVENT("Custom Event"),
    REQUEST("Network Request"),
    NETWORK_ERROR("Network Error");
}

