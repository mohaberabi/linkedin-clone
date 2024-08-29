package com.mohaberabi.linkedinclone.presentation.activity

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mohaberabi.linkedin.core.domain.util.AppBottomSheet
import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.BottomSheetAction
import com.mohaberabi.linkedin.core.domain.util.NavigationCommand
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.job_detail.presentation.fragment.JobDetailFragment
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import kotlinx.coroutines.launch


fun MainActivity.rootNavController(): NavController {
    val navHostFragment = supportFragmentManager
        .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    return navController
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
