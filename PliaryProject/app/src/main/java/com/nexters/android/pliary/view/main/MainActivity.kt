package com.nexters.android.pliary.view.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.nexters.android.pliary.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private val navController: NavController
        get() = findNavController(R.id.navigation_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if(navController.currentDestination?.id == R.id.homeFragment) {
            finish()
        }
        navController.navigateUp()
    }
}
