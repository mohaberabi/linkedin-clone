package com.mohaberabi.presentation.ui.util

import androidx.annotation.AnimRes
import com.mohaberabi.core.presentation.ui.R.anim.enter_bottom_top
import com.mohaberabi.core.presentation.ui.R.anim.exit_bottom_to_top

import com.mohaberabi.core.presentation.ui.R.anim.enter_left_right
import com.mohaberabi.core.presentation.ui.R.anim.exit_left_right

sealed class NavTransition(
    @AnimRes val enterAnim: Int,
    @AnimRes val exitAnim: Int,
    @AnimRes val popEnterAnim: Int,
    @AnimRes val popExitAnim: Int,
) {
    data object LeftToRight : NavTransition(
        enterAnim = enter_left_right,
        exitAnim = exit_left_right,
        popExitAnim = exit_left_right,
        popEnterAnim = enter_left_right
    )

    data object BottomToTop : NavTransition(
        enterAnim = enter_bottom_top,
        exitAnim = exit_bottom_to_top,
        popExitAnim = exit_bottom_to_top,
        popEnterAnim = enter_bottom_top
    )
}