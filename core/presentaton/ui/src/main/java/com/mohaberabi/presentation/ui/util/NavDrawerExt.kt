package com.mohaberabi.presentation.ui.util

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout


fun DrawerLayout.closeDrawer() = closeDrawer(GravityCompat.START)
fun DrawerLayout.openDrawer() = openDrawer(GravityCompat.START)

fun DrawerLayout.lock() = setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
fun DrawerLayout.unlock() = setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
inline fun DrawerLayout.closeAndDo(
    action: () -> Unit
) {
    closeDrawer(GravityCompat.START)
    action()
}

fun DrawerLayout.toggleEnabled(
    enabled: Boolean,
) = if (enabled) unlock() else lock()
