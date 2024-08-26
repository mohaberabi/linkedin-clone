package com.mohaberabi.linkedinclone.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linkedin.core.domain.util.AppBottomSheetShower
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import com.mohaberabi.linkedin.core.domain.util.GlobalNavigator
import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityViewModel
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.presentation.ui.navigation.NavDeepLinks
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate
import com.mohaberabi.presentation.ui.util.closeDrawer
import com.mohaberabi.presentation.ui.util.openDrawer
import com.mohaberabi.presentation.ui.util.addDefaultPaddings
import com.mohaberabi.presentation.ui.util.collectLifeCycleFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<MainActivityViewModel>()


    @Inject
    lateinit var anayltics: AppAnalytics

    @Inject
    lateinit var sheetShower: AppBottomSheetShower

    @Inject
    lateinit var appGlobalNavigator: GlobalNavigator

    @Inject
    lateinit var drawerController: DrawerController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        anayltics.logEvent(
            "activityCreated",
        )
        enableEdgeToEdge()
        val splash = installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splash.setKeepOnScreenCondition {
            !viewmodel.state.value.didLoad
        }
        addDefaultPaddings(
            rootView = binding.root,
        )

        observeState()
        observeGlobalDrawer(
            onOpenDrawer = {
                binding.drawerLayout.openDrawer()
            },
            onCloseDrawer = {
                binding.drawerLayout.closeDrawer()
            }
        )

        observeGlobalBottomSheet()
        observeGlobalNavCommands()
    }


    private fun observeState() {
        collectLifeCycleFlow(
            viewmodel.state,
        ) { state ->
            handleStartNavGraph(
                loggedIn = state.user != null,
                didLoad = state.didLoad
            )
            binding.bind(
                state = state,
                onAvatarClick = { goToProfile() },
            )
        }
    }


    private fun goToProfile() {
        binding.drawerLayout.closeDrawer()
        rootNavController().deepLinkNavigate(NavDeepLinks.PROFILE)
    }


    private fun handleStartNavGraph(
        loggedIn: Boolean,
        didLoad: Boolean
    ) {
        val navController = rootNavController()
        if (!loggedIn && didLoad) {
            navController.setGraph(com.mohaberabi.register.R.navigation.nav_graph_register)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}


