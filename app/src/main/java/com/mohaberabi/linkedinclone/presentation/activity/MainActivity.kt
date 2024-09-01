package com.mohaberabi.linkedinclone.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linkedin.core.domain.util.AppBottomSheetShower
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.core.data.source.fake.FakeComments
import com.mohaberabi.linkedinclone.core.data.source.fake.FakeJobDetails
import com.mohaberabi.linkedinclone.core.data.source.fake.FakeJobs
import com.mohaberabi.linkedinclone.core.data.source.fake.FakePosts
import com.mohaberabi.linkedinclone.core.data.source.fake.FakeReactions
import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityViewModel
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.closeDrawer
import com.mohaberabi.presentation.ui.util.extension.addDefaultPaddings
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<MainActivityViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val bottomNavViews = setOf(
        com.mohaberabi.posts.R.id.postsFragment,
        com.mohaberabi.jobs.R.id.jobsFragments,
        com.mohaberabi.user_media.R.id.profilePictureFragment,
    )

    @Inject
    lateinit var anayltics: AppAnalytics

    @Inject
    lateinit var sheetShower: AppBottomSheetShower


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        anayltics.logEvent(
            "activityCreated",
        )
        enableEdgeToEdge()
        val splash = installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(
            binding.root,
        )
        setSupportActionBar(
            binding.appBar,
        )
        setupAppBar()
        splash.setKeepOnScreenCondition {
            !viewmodel.state.value.didLoad
        }
        addDefaultPaddings(
            rootView = binding.root,
        )
        observeState()
        observeGlobalBottomSheet()
        with(
            binding,
        ) {
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
            bottomNavViews,
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
            graph.setStartDestination(com.mohaberabi.register.R.id.nav_graph_register)
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


