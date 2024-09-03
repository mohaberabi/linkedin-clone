package com.mohaberabi.linkedinclone.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linkedin.core.domain.util.AppBottomSheetShower
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityViewModel
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.closeDrawer
import com.mohaberabi.presentation.ui.util.extension.addDefaultPaddings
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<MainActivityViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val nonAutoHandledAppBarViews = setOf(
        com.mohaberabi.posts.R.id.postsFragment,
        com.mohaberabi.jobs.R.id.jobsFragments,
        com.mohaberabi.user_media.R.id.profilePictureFragment,
        com.mohaberabi.onboarding.R.id.onBoardingFragment,
    )

    @Inject
    lateinit var anayltics: AppAnalytics

    @Inject
    lateinit var sheetShower: AppBottomSheetShower


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        anayltics.logEvent("activityCreated")
        enableEdgeToEdge()
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { !viewmodel.state.value.didLoad }
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBar)
        setupAppBar()
        addDefaultPaddings(rootView = binding.root)
        observeState()
        observeGlobalBottomSheet()
        with(binding) {
            bottomNavigationView.setupWithNavController(
                navController = rootNavController(),
            )
            listenToNavGraphDestinations(
                navController = rootNavController(),
            )
        }

    }

    private fun observeState() {
        collectLifeCycleFlow(
            viewmodel.state,
        ) { state ->
            handleStartRoute(
                loadedData = state.didLoad,
                loggedIn = state.user != null
            )
            binding.bind(
                state = state,
                onGoProfile = { goToProfile() }
            )
        }
    }


    private fun goToProfile() {
        binding.appDrawerLayout.closeDrawer()
        rootNavController().goTo(AppRoutes.Profile)
    }


    private fun setupAppBar() {
        appBarConfiguration = AppBarConfiguration(
            nonAutoHandledAppBarViews,
        )
        NavigationUI.setupActionBarWithNavController(
            this,
            rootNavController(),
            appBarConfiguration
        )
    }


    private fun handleStartRoute(
        loggedIn: Boolean,
        loadedData: Boolean,
    ) {
        if (!loggedIn && loadedData) {
            val graph = rootNavController().navInflater.inflate(R.navigation.nav_graph_main)
            graph.setStartDestination(com.mohaberabi.onboarding.R.id.nav_graph_onboarding)
            rootNavController().graph = graph
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return rootNavController().navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


