package com.mohaberabi.linkedinclone.add_post.presentation.fragments

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mohaberabi.add_posts.databinding.FragmentAddPostBinding
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostActions
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostEvents
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostViewModel
import com.mohaberabi.presentation.ui.util.asByteArray
import com.mohaberabi.presentation.ui.util.createLoadingDialog
import com.mohaberabi.presentation.ui.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class AddPostFragment : Fragment() {


    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddPostViewModel>()

    private lateinit var renderer: AddPostRenderer
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
    ): View? {

        _binding = FragmentAddPostBinding.inflate(
            layoutInflater,
            container,
            false
        )
        renderer = AddPostRenderer(
            binding = binding,
            onAction = viewModel::action
        )
        binding.addPhotoButton.setOnClickListener {
            imagePicker.launch("image/*")
        }
        val loadingDialog = requireContext().createLoadingDialog()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderer.render(state)
                if (state.loading) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                }
            }

        }



        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is AddPostEvents.Error -> binding.root.showSnackBar(
                        event.error.asString(
                            requireContext()
                        )
                    )

                    AddPostEvents.Posted -> {
                        binding.root.showSnackBar(
                            "Post Added"
                        )
                        findNavController().popBackStack()
                    }
                }
            }
        }

        return binding.root
    }


}

