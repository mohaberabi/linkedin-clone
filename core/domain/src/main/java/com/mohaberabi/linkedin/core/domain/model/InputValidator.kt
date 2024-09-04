package com.mohaberabi.linkedin.core.domain.model


data class InputValidator(
    val status: InputValidatorStatus = InputValidatorStatus.Initial,
    val reason: InputInvalidReason? = null,
)


enum class InputValidatorStatus {
    Initial,
    Valid,
    NOT_VALID;

    val isValid: Boolean
        get() = this == Valid
}

enum class InputInvalidReason {
    EMPTY_FIELD,
    INVALID_EMAIL,
    WEAK_PASSWORD
}

