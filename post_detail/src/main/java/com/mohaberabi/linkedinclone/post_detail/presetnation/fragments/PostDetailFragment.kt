package com.mohaberabi.linkedinclone.post_detail.presetnation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohaberabi.linkedinclone.post_detail.R


class PostDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_post_detail,
            container,
            false
        )
    }


}