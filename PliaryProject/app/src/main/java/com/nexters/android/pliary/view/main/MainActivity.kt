package com.nexters.android.pliary.view.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.nexters.android.pliary.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val navController: NavController
        get() = findNavController(R.id.navigation_fragment)

    /*@Inject
    lateinit var job: JobSchedulerStart*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //job.start(this)

        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onBackPressed() {
        if(navController.currentDestination?.id == R.id.homeFragment) {
            finish()
        }
        navController.navigateUp()
    }
}
