package com.mohaberabi.linkedinclone.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.current_user.presentation.CurrentUserViewModel
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.addDefaultPaddings
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.openDrawer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.mohaberabi.onboarding.R.id.nav_graph_onboarding
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.screenClosed
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.screenOpened
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.RemoteLogging
import com.mohaberabi.linkedinclone.presentation.activity.constants.AppConstants.appbarRoutesConfig
import com.mohaberabi.linkedinclone.presentation.activity.constants.toRouteType
import com.mohaberabi.linkedinclone.presentation.activity.ext.addNotificationsBadge
import com.mohaberabi.linkedinclone.presentation.activity.ext.bindWithActivityState
import com.mohaberabi.linkedinclone.presentation.activity.ext.bindWithCurrentUserState
import com.mohaberabi.linkedinclone.presentation.activity.ext.logActivityCreated
import com.mohaberabi.linkedinclone.presentation.activity.ext.logActivityDestroyed
import com.mohaberabi.linkedinclone.presentation.activity.ext.rootNavController
import com.mohaberabi.linkedinclone.presentation.activity.ext.setupAppBottomNavigation
import com.mohaberabi.linkedinclone.presentation.activity.ext.setupDrawerNavigation

import com.mohaberabi.linkedinclone.presentation.utils.AppFragmentLifeCycleListener
import com.mohaberabi.linkedinclone.user_metadata.UserMetaDataViewModel

import com.mohaberabi.presentation.ui.util.closeAndDo

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var remoteLogging: RemoteLogging

    @Inject
    lateinit var anayltics: AppAnalytics
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val currentUserViewModel by viewModels<CurrentUserViewModel>()
    private val userMetaDataViewModel by viewModels<UserMetaDataViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val appFragmentLifeCycleListener by lazy {
        AppFragmentLifeCycleListener(
            onDestroy = { fragmentName ->
                anayltics.screenClosed(fragmentName)
            },
            onCreate = { fragmentName ->
                anayltics.screenOpened(fragmentName)
            }
        )
    }


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
        collectUserState()
        collectUserMetaData()
        registerFragmentListener()
        setupRouteListener()
        anayltics.logActivityCreated()
    }


    private fun setupBinding() {
        with(binding) {
            setupDrawerNavigation(
                onDrawerNavigate = { route ->
                    appDrawerLayout.closeAndDo {
                        rootNavController.goTo(route)
                    }
                }
            )
            bottomNavigationView.setupAppBottomNavigation(
                navController = rootNavController,
                onReadAllNotifications = {
                    userMetaDataViewModel.markAllNotificationsRead()
                }
            )
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


    private fun setupRouteListener() {
        rootNavController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bindWithActivityState(
                type = destination.id.toRouteType,
                title = destination.label?.toString()
            )
        }
    }


    private fun setupAppBar() {
        appBarConfiguration =
            AppBarConfiguration(appbarRoutesConfig)
        NavigationUI.setupActionBarWithNavController(
            this,
            rootNavController,
            appBarConfiguration
        )
        binding.appBar.setOnAvatarClickListener {
            binding.appDrawerLayout.openDrawer()

        }
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


    override fun onSupportNavigateUp(): Boolean {
        return rootNavController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        unRegisterFragmentListener()
        anayltics.logActivityDestroyed()

    }

}


