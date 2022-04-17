package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.R
import com.example.myapplication.util.NetworkConnectionUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)
        observeEvents()
    }

    private fun observeEvents() {
        val connectivity = NetworkConnectionUtil(application)
        connectivity.observe(this) { isConnected ->
            if (!isConnected)
                showError(resources.getString(R.string.internet_error))
        }
    }

}