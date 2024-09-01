package com.mohaberabi.linkedin.core.domain.usecase.validator

import com.mohaberabi.linkedin.core.domain.model.InputInvalidReason
import com.mohaberabi.linkedin.core.domain.model.InputValidator
import com.mohaberabi.linkedin.core.domain.model.InputValidatorStatus


class ValidatePasswordUseCase {
    operator fun invoke(
        password: String,
    ): InputValidator {
        val reason: InputInvalidReason? = when {
            password.trim().length < 8 -> InputInvalidReason.WEAK_PASSWORD
            else -> null
        }
        return if (reason != null) {
            InputValidator(
                reason = reason,
                status = InputValidatorStatus.NOT_VALID
            )
        } else {
            InputValidator(status = InputValidatorStatus.Valid)
        }
    }

}