package com.mohaberabi.linkedinclone.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mohaberabi.linkedinclone.login.viewmodel.LoginActions
import com.mohaberabi.linkedinclone.login.viewmodel.LoginEvents
import com.mohaberabi.linkedinclone.login.viewmodel.LoginState
import com.mohaberabi.linkedinclone.login.viewmodel.LoginViewModel
import com.mohaberabi.login.databinding.FragmentLoginBinding
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.popAllAndNavigate
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import com.mohaberabi.presentation.ui.util.stringId
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(
            layoutInflater,
            container,
            false
        )


        setupBinding()

        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            bindWithState(state)
        }

        collectLifeCycleFlow(
            viewModel.events,
        ) { event ->
            when (event) {
                is LoginEvents.Error -> binding.root.showSnackBar(event.error)
                LoginEvents.LoggedIn -> goHome()
            }
        }

        return binding.root
    }


    private fun setupBinding() {
        with(binding) {
            emailOrPhoneField.addTextChangedListener {
                viewModel.onAction(LoginActions.EmailChanged(it.toString()))
            }
            passwordField.addTextChangedListener {
                viewModel.onAction(LoginActions.PasswordChanged(it.toString()))
            }
            signInButton.setButtonClickListener {
                viewModel.onAction(LoginActions.SubmitLogin)
            }
            joinLinkedIn.setOnClickListener {
                goSignUp()
            }
        }
    }

    private fun bindWithState(state: LoginState) {
        with(binding) {
            val buttonEnabled =
                state.emailValidState.status.isValid && state.passwordValidState.status.isValid
            signInButton.setLoading(state.loading)
            signInButton.setEnable(buttonEnabled)
            emailOrPhoneField.error = state.emailValidState.reason?.stringId
                ?.let { getString(it) }
            passwordField.error = state.passwordValidState.reason?.stringId
                ?.let { getString(it) }
        }
    }

    private fun goHome() {
        findNavController().popAllAndNavigate(AppRoutes.Posts)
    }

    private fun goSignUp() {

    }
}