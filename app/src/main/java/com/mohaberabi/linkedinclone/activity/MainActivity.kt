package com.mohaberabi.linkedinclone.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
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

    @Inject
    lateinit var drawerController: DrawerController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addDefaultPaddings(binding.root)
        lifecycleScope.launch {
            withContext(Dispatchers.Main.immediate) {
                drawerController.collect { event ->
                    when (event) {
                        AppDrawerActions.Close -> binding.drawerLayout.openDrawer(GravityCompat.START)
                        AppDrawerActions.Open -> binding.drawerLayout.openDrawer(GravityCompat.START)
                    }
                }

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}