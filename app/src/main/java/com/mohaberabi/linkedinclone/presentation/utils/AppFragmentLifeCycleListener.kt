package com.mohaberabi.linkedinclone.presentation.utils

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.screenClosed
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class AppFragmentLifeCycleListener(
    private val onCreate: (String) -> Unit,
    private val onDestroy: (String) -> Unit,
) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentDestroyed(fm: FragmentManager, fragment: Fragment) {
        super.onFragmentDestroyed(fm, fragment)
        val name = fragment.javaClass.simpleName
        onDestroy(name)
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        fragment: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, fragment, v, savedInstanceState)
        val name = fragment.javaClass.simpleName
        onCreate(name)

    }


}

