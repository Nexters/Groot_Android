package com.nexters.android.pliary.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

    }
}
