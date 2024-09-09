package com.mohaberabi.linkedinclone.presentation.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


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

