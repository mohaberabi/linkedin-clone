package com.mohaberabi.presentation.ui.util

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout


fun DrawerLayout.closeDrawer() = closeDrawer(GravityCompat.START)
fun DrawerLayout.openDrawer() = openDrawer(GravityCompat.START)