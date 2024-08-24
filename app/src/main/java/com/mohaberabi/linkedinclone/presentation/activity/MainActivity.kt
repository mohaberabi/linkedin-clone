package com.mohaberabi.linkedinclone.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import com.mohaberabi.linkedinclone.presentation.viewmodel.MainActivityViewModel
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.presentation.ui.navigation.NavDeepLinks
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate
import com.mohaberabi.presentation.ui.navigation.popAllAndNavigate
import com.mohaberabi.presentation.ui.util.closeDrawer
import com.mohaberabi.presentation.ui.util.openDrawer
import com.mohaberabi.presentation.ui.views.addDefaultPaddings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<MainActivityViewModel>()
    private lateinit var renderer: MainActivityRenderer

    @Inject
    lateinit var drawerController: DrawerController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splash = installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        renderer = MainActivityRenderer(
            binding = binding,
            onAvatarClick = {
                goToProfile()
            },
        )
        setContentView(binding.root)
        addDefaultPaddings(binding.root)
        splash.setKeepOnScreenCondition {
            !viewmodel.state.value.didLoad
        }
        observeDrawer()
        observeState()
    }


    private fun observeDrawer() {

        lifecycleScope.launch {
            withContext(Dispatchers.Main.immediate) {
                drawerController.collect { event ->
                    when (event) {
                        AppDrawerActions.Close -> binding.drawerLayout.closeDrawer()
                        AppDrawerActions.Open -> binding.drawerLayout.openDrawer()
                    }
                }
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.state.collect { state ->
                    renderer.bind(state)
                    if (state.user != null) {
                        goToLayout()
                    }

                }
            }

        }
    }


    private fun goToLayout() = rootNavController().popAllAndNavigate(R.id.layoutFragment)

    private fun goToProfile() {
        binding.drawerLayout.closeDrawer()
        rootNavController().deepLinkNavigate(NavDeepLinks.PROFILE)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
