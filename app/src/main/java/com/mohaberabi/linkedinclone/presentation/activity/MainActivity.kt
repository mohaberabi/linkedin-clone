package com.mohaberabi.linkedinclone.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mohaberabi.linkedin.core.domain.util.AppBottomSheet
import com.mohaberabi.linkedin.core.domain.util.AppBottomSheetShower
import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.BottomSheetAction
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityViewModel
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.job_detail.presentation.fragment.JobDetailFragment
import com.mohaberabi.presentation.ui.navigation.NavDeepLinks
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate
import com.mohaberabi.presentation.ui.navigation.popAllAndNavigate
import com.mohaberabi.presentation.ui.util.closeDrawer
import com.mohaberabi.presentation.ui.util.openDrawer
import com.mohaberabi.presentation.ui.util.addDefaultPaddings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<MainActivityViewModel>()

    @Inject
    lateinit var sheetShower: AppBottomSheetShower

    @Inject
    lateinit var drawerController: DrawerController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splash = installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addDefaultPaddings(binding.root)
        splash.setKeepOnScreenCondition {
            !viewmodel.state.value.didLoad
        }
        observeState()
        observeDrawer()
        observeBottomSheet()
    }


    private fun observeBottomSheet() {
        lifecycleScope.launch {
            sheetShower.actions.collect { action ->
                when (action) {
                    BottomSheetAction.Dismiss -> Unit
                    is BottomSheetAction.Show -> {
                        when (val sheet = action.appSheet) {
                            is AppBottomSheet.JobDetailSheet -> {
                                val fragment = JobDetailFragment.newInstance(sheet.jobId)
                                fragment.show(supportFragmentManager, sheet.tag)

                            }
                        }

                    }
                }
            }
        }
    }

    private fun observeState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.state.collect { state ->
                    if (state.user != null) {
                        binding.bind(
                            state = state,
                            onAvatarClick = { goToProfile() },
                        )
                        goToLayout()
                    }

                }
            }
        }
    }


    private fun observeDrawer() {
        lifecycleScope.launch {
            drawerController.collect { event ->
                when (event) {
                    AppDrawerActions.Close -> binding.drawerLayout.closeDrawer()
                    AppDrawerActions.Open -> binding.drawerLayout.openDrawer()
                }
            }
        }
    }


    private fun goToLayout() =
        rootNavController().popAllAndNavigate(R.id.layoutFragment)

    private fun goToProfile() {
        binding.drawerLayout.closeDrawer()
        rootNavController().deepLinkNavigate(NavDeepLinks.PROFILE)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
