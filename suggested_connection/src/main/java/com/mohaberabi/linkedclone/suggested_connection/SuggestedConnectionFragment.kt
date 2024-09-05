package com.mohaberabi.linkedclone.suggested_connection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.mohaberabi.linkedinclone.core.data.source.fake.FakeUsers
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberabi.suggested_connection.R
import com.mohaberabi.suggested_connection.databinding.FragmentSuggestedConnectionBinding


class SuggestedConnectionFragment : Fragment() {


    private var _binding: FragmentSuggestedConnectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSuggestedConnectionBinding.inflate(
            layoutInflater,
            container,
            false
        )
        val adapter = SuggestedConnectionsAdapter()
        adapter.submitList(FakeUsers.fakeUsers)
        with(binding) {
            connectionRecyclerView.submitOnce(
                newLayoutManager = GridLayoutManager(requireContext(), 2),
                listAdapter = adapter
            )
        }
        return binding.root
    }

}