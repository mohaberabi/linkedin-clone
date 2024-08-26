package com.mohaberabi.linkedinclone.presentation.activity

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedin.core.domain.util.AppBottomSheet
import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.BottomSheetAction
import com.mohaberabi.linkedin.core.domain.util.NavigationCommand
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.linkedinclone.job_detail.presentation.fragment.JobDetailFragment
import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityState
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate
import com.mohaberabi.presentation.ui.util.cachedImage
import com.mohaberabi.presentation.ui.util.closeDrawer
import com.mohaberabi.presentation.ui.util.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.openDrawer
import kotlinx.coroutines.launch


fun MainActivity.rootNavController(): NavController {
    val navHostFragment = supportFragmentManager
        .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    return navController
}

fun MainActivity.observeGlobalDrawer(
    onCloseDrawer: () -> Unit,
    onOpenDrawer: () -> Unit,
) {
    lifecycleScope.launch {
        drawerController.collect { event ->
            when (event) {
                AppDrawerActions.Close -> onCloseDrawer()
                AppDrawerActions.Open -> onOpenDrawer()
            }
        }
    }
}

fun MainActivity.observeGlobalBottomSheet() {
    collectLifeCycleFlow(
        sheetShower.actions,
    ) { action ->
        when (action) {
            BottomSheetAction.Dismiss -> Unit
            is BottomSheetAction.Show -> handleGlobalBottomSheetOpened(action.appSheet)
        }
    }
}

fun MainActivity.observeGlobalNavCommands() {
    collectLifeCycleFlow(
        appGlobalNavigator.commands,
    ) { command ->
        when (command) {
            is NavigationCommand.GoTo -> {
                rootNavController().deepLinkNavigate(command.destinationUri)
            }
        }
    }
}

private fun MainActivity.handleGlobalBottomSheetOpened(
    sheet: AppBottomSheet,
) {
    when (sheet) {
        is AppBottomSheet.JobDetailSheet -> {
            val fragment = JobDetailFragment.newInstance(sheet.jobId)
            fragment.show(supportFragmentManager, sheet.tag)
        }
    }

}
