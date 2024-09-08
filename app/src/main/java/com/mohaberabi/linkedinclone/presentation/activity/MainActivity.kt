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
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.current_user.presentation.CurrentUserViewModel
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
import com.mohaberabi.onboarding.R.id.nav_graph_onboarding
import com.mohaberabi.posts.R.id.postsFragment
import com.mohaberabi.jobs.R.id.jobsFragments
import com.mohaberabi.in_app_notifications.R.id.inAppNotificationsFragment
import com.mohaberabi.in_app_notifications.R.id.nav_graph_app_notifications
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.screenClosed
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.screenOpened

import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityViewModel
import com.mohaberabi.linkedinclone.presentation.utils.AppFragmentLifeCycleListener
import com.mohaberabi.linkedinclone.user_metadata.UserMetaDataViewModel
import com.mohaberabi.user_media.R.id.profilePictureFragment
import com.mohaberabi.onboarding.R.id.onBoardingFragment
import com.mohaberabi.presentation.ui.util.extension.showIf
import com.mohaberabi.suggested_connection.R.id.suggestedConnectionFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var anayltics: AppAnalytics
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val currentUserViewModel by viewModels<CurrentUserViewModel>()
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    private val userMetaDataViewModel by viewModels<UserMetaDataViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val appFragmentLifeCycleListener by lazy {
        AppFragmentLifeCycleListener(
            onDestroy = {
                anayltics.screenClosed(it)
            },
            onCreate = {
                anayltics.screenOpened(it)
            }
        )
    }

    private val nonAutoHandledAppBarViews = setOf(
        postsFragment,
        jobsFragments,
        profilePictureFragment,
        onBoardingFragment,
        suggestedConnectionFragment,
        inAppNotificationsFragment,
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition { !currentUserViewModel.state.value.didLoad }
        }
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addDefaultPaddings(binding.root)
        setSupportActionBar(binding.appBar)
        setupAppBar()
        setupBinding()
        collectActivityState()
        collectUserState()
        collectUserMetaData()
        registerFragmentListener()
        anayltics.logEvent("activityCreated")
    }


    private fun collectActivityState() {
        collectLifeCycleFlow(
            mainActivityViewModel.state,
        ) { state ->
            with(binding) {
                bottomNavigationView.showIf(state.showBottomNav)
                appBar.apply {
                    showAvatar(state.showUserAvatar)
                    showSearchField(state.showSearchField)
                    toggleTitleVisiblity(state.toolBarTitle != null)
                    state.toolBarTitle?.let {
                        setRouteTitle(it.toString())
                    }
                }

            }
        }
    }

    private fun collectUserState() {
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

    private fun collectUserMetaData() {
        collectLifeCycleFlow(
            userMetaDataViewModel.state,
        ) { metaData ->
            binding.bottomNavigationView.addNotificationsBadge(
                metaData.unreadNotifications.toInt(),
            )
        }
    }

    private fun setupBinding() {
        with(binding) {
            val headerView = appDrawerView.getHeaderView(0)
            val mainDrawerBinding = NavHeaderBinding.bind(headerView)
            appBar.setOnAvatarClickListener {
                appDrawerLayout.openDrawer()
            }
            with(mainDrawerBinding) {
                avatarImage.setOnClickListener {
                    goFromDrawer(AppRoutes.Profile())
                }
                settingsButton.setOnClickListener {
                    goFromDrawer(AppRoutes.Settings)

                }
                savedPosts.setOnClickListener {
                    goFromDrawer(AppRoutes.SavedPosts)
                }
                profileViews.setOnClickListener {
                    goFromDrawer(AppRoutes.ProfileViews)
                }
            }
        }
        setupBottomNav()
        setupRouteListener()
    }

    private fun setupBottomNav() {
        binding.bottomNavigationView.apply {
            setupWithNavController(rootNavController)
            setOnItemSelectedListener { item ->
                if (item.itemId == R.id.nav_item_addPost) {
                    rootNavController.goTo(AppRoutes.AddPost)
                } else {
                    if (item.itemId == nav_graph_app_notifications) {
                        userMetaDataViewModel.markAllNotificationsRead()
                    }
                    NavigationUI.onNavDestinationSelected(item, rootNavController)
                }
                true
            }
        }
    }

    private fun setupRouteListener() {
        rootNavController.addOnDestinationChangedListener { _, destination, _ ->
            mainActivityViewModel.changeDestination(
                id = destination.id,
                title = destination.label,
            )
        }
    }


    private fun goFromDrawer(route: AppRoutes) {
        binding.appDrawerLayout.closeDrawer()
        rootNavController.goTo(route)
    }

    private fun setupAppBar() {
        appBarConfiguration = AppBarConfiguration(nonAutoHandledAppBarViews)
        NavigationUI.setupActionBarWithNavController(
            this,
            rootNavController,
            appBarConfiguration
        )
    }


    private fun handleStartRoute(
        loggedIn: Boolean,
        loadedData: Boolean,
    ) {
        if (!loggedIn && loadedData) {
            val graph = rootNavController.navInflater.inflate(R.navigation.nav_graph_main)
            graph.setStartDestination(nav_graph_onboarding)
            rootNavController.graph = graph
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return rootNavController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun unRegisterFragmentListener() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(
            appFragmentLifeCycleListener,
        )
    }

    private fun registerFragmentListener() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            appFragmentLifeCycleListener,
            true
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        unRegisterFragmentListener()
    }

}


