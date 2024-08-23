package com.mohaberabi.linkedinclone.register.presentation.fragment

import androidx.core.widget.addTextChangedListener
import com.mohaberabi.linkedinclone.register.presentation.viewmodel.RegisterActions
import com.mohaberabi.linkedinclone.register.presentation.viewmodel.RegisterState
import com.mohaberabi.register.databinding.FragmentRegisterBinding


class RegisterRenderer(
    private val onAction: (RegisterActions) -> Unit,
    private val binding: FragmentRegisterBinding,
) {


    fun bind(state: RegisterState) {

    }

    fun bind() {
        bindName()
        bindBio()
        bindLastName()
        bindButton()
        bindPassword()
        bindEmail()
    }


    private fun bindName() {
        binding.firstNameField.addTextChangedListener {
            onAction(RegisterActions.NameChanged(it.toString()))
        }
    }

    private fun bindLastName() {
        binding.lastNameField.addTextChangedListener {
            onAction(RegisterActions.LastNameChanged(it.toString()))
        }
    }

    private fun bindEmail() {
        binding.emailField.addTextChangedListener {
            onAction(RegisterActions.EmailChanged(it.toString()))
        }
    }

    private fun bindPassword() {
        binding.passwordField.addTextChangedListener {
            onAction(RegisterActions.PasswordChanged(it.toString()))
        }
    }

    private fun bindBio() {
        binding.bioTextField.addTextChangedListener {
            onAction(RegisterActions.BioChanged(it.toString()))
        }
    }

    private fun bindButton() {
        binding.registerButton.setOnClickListener {
            onAction(RegisterActions.OnRegisterClick)
        }
    }
}