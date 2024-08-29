package com.mohaberabi.linkedinclone.add_post.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mohaberabi.add_posts.databinding.FragmentAddPostBinding
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostActions
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostEvents
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostViewModel
import com.mohaberabi.presentation.ui.util.extension.asByteArray
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.createLoadingDialog
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddPostFragment : Fragment() {


    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddPostViewModel>()

    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent(),
    ) { uri ->
        val bytes = uri?.asByteArray(requireContext().contentResolver)
        bytes?.let {
            viewModel.action(AddPostActions.SavePostImage(it))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddPostBinding.inflate(
            layoutInflater,
            container,
            false
        )
        binding.addPhotoButton.setOnClickListener {
            imagePicker.launch("image/*")
        }
        val loadingDialog = requireContext().createLoadingDialog()


        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.render(
                state = state,
                onAction = viewModel::action
            )
            if (state.loading) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        }




        collectLifeCycleFlow(
            viewModel.events,
        ) { event ->
            when (event) {
                is AddPostEvents.Error -> {
                    val errorMessage = event.error.asString(
                        requireContext()
                    )
                    binding.root.showSnackBar(errorMessage)
                }

                AddPostEvents.Posted -> findNavController().popBackStack()

            }
        }

        return binding.root
    }


}

