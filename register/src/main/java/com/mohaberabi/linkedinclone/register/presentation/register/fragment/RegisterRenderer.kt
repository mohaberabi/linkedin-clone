package com.mohaberabi.linkedinclone.register.presentation.register.fragment

import androidx.core.widget.addTextChangedListener
import com.mohaberabi.linkedinclone.register.presentation.register.viewmodel.RegisterActions
import com.mohaberabi.linkedinclone.register.presentation.register.viewmodel.RegisterState
import com.mohaberabi.register.databinding.FragmentRegisterBinding


fun FragmentRegisterBinding.setup(
    onAction: (RegisterActions) -> Unit
) {
    emailField.addTextChangedListener {
        onAction(RegisterActions.EmailChanged(it.toString()))
    }
    firstNameField.addTextChangedListener {
        onAction(RegisterActions.NameChanged(it.toString()))
    }
    lastNameField.addTextChangedListener {
        onAction(RegisterActions.LastNameChanged(it.toString()))
    }

    passwordField.addTextChangedListener {
        onAction(RegisterActions.PasswordChanged(it.toString()))
    }
    bioTextField.addTextChangedListener {
        onAction(RegisterActions.BioChanged(it.toString()))
    }

    registerButton.setButtonClickListener {
        onAction(RegisterActions.OnRegisterClick)
    }

}


fun FragmentRegisterBinding.bindWithRegisterState(
    state: RegisterState
) {
    registerButton.setLoading(state.loading)
}