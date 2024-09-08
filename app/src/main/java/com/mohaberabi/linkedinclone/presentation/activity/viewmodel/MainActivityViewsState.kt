package com.mohaberabi.linkedinclone.presentation.activity.viewmodel


data class MainActivityViewsState(
    val showUserAvatar: Boolean = true,
    val showSearchField: Boolean = true,
    val enableDrawer: Boolean = true,
    val showBottomNav: Boolean = true,
    val toolBarTitle: CharSequence? = null,
) {
    val nonTopLevel
        get() = MainActivityViewsState(
            showBottomNav = false,
            showUserAvatar = false,
            showSearchField = false,
        )
}

