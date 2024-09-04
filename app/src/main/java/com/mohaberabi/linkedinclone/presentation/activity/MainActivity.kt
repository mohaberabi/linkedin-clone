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
import com.mohaberabi.linkedinclone.current_user.CurrentUserViewModel
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.closeDrawer
import com.mohaberabi.presentation.ui.util.extension.addDefaultPaddings
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.openDrawer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.fragment.app.activityViewModels


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val currentUserViewModel by viewModels<CurrentUserViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var anayltics: AppAnalytics

    @Inject
    lateinit var sheetShower: AppBottomSheetShower
    private val nonAutoHandledAppBarViews = setOf(
        com.mohaberabi.posts.R.id.postsFragment,
        com.mohaberabi.jobs.R.id.jobsFragments,
        com.mohaberabi.user_media.R.id.profilePictureFragment,
        com.mohaberabi.onboarding.R.id.onBoardingFragment,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        anayltics.logEvent("activityCreated")
        enableEdgeToEdge()
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { !currentUserViewModel.state.value.didLoad }
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBar)
        setupAppBar()
        setupBinding()
        addDefaultPaddings(rootView = binding.root)
        observeState()
        observeGlobalBottomSheet()
    }

    private fun setupBinding() {

        with(binding) {
            val headerView = appDrawerView.getHeaderView(0)
            val mainDrawerBinding = NavHeaderBinding.bind(headerView)
            appBar.setOnAvatarClickListener {
                appDrawerLayout.openDrawer()
            }
            mainDrawerBinding.avatarImage.setOnClickListener {
                goToProfile()
            }
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
            currentUserViewModel.state,
        ) { state ->
            handleStartRoute(
                loadedData = state.didLoad,
                loggedIn = state.user != null
            )
            binding.bindWithCurrentUserState(state = state)
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


