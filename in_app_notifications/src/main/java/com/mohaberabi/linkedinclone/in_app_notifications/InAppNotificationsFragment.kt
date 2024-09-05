package com.mohaberabi.linkedinclone.in_app_notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohaberabi.in_app_notifications.R

class InAppNotificationsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_in_app_notifications,
            container,
            false
        )
    }
}