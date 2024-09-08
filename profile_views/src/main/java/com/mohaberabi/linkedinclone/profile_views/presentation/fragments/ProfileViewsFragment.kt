package com.mohaberabi.linkedinclone.profile_views.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mohaberabi.linkedinclone.profile_views.presentation.fragments.list_adapter.ProfileViewersListAdapter
import com.mohaberabi.linkedinclone.profile_views.presentation.viewmodel.ProfileViewsActions
import com.mohaberabi.linkedinclone.profile_views.presentation.viewmodel.ProfileViewsViewModel
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.profile_views.R
import com.mohaberabi.profile_views.databinding.FragmentProfileViewsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileViewsFragment : Fragment() {


    private lateinit var profileViewsAdapter: ProfileViewersListAdapter

    private val viewmodel by viewModels<ProfileViewsViewModel>()
    private var _binding: FragmentProfileViewsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileViewsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        profileViewsAdapter = ProfileViewersListAdapter(
            onClick = {
                goToProfile(it)
            },
        )
        binding.setup(
            listAdapter = profileViewsAdapter,
            onAction = viewmodel::onAction
        )
        collectLifeCycleFlow(
            viewmodel.state,
        ) { state ->
            binding.bindFromState(
                state = state,
                adapter = profileViewsAdapter
            )
        }
        return binding.root
    }

    private fun goToProfile(uid: String) =
        findNavController().goTo(AppRoutes.Profile(uid = uid))

}