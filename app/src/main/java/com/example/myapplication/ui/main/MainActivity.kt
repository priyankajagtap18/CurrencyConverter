package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.R
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.util.NetworkConnectionUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
    }

    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)
        observeNetworkEvents()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment?.navController?.navigateUp() ?: false
    }

    private fun observeNetworkEvents() {
        val connectivity = NetworkConnectionUtil(application)
        connectivity.observe(this) { isConnected ->
            if (!isConnected)
                showError(resources.getString(R.string.internet_error))
        }
    }

}