package com.mohaberabi.linkedin.core.domain.usecase.validator

data class ValidatorUseCases(
    val validatePassword: ValidatePasswordUseCase = ValidatePasswordUseCase(),
    val validateEmail: ValidateEmailUseCase = ValidateEmailUseCase()
)
