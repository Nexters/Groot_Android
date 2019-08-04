package com.nexters.android.pliary.view.splash

import android.content.Intent
import android.os.Bundle
import com.nexters.android.pliary.R
import com.nexters.android.pliary.view.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.*

class SplashActivity : DaggerAppCompatActivity() {

    lateinit var job : Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        job = GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}