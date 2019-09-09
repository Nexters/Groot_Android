package com.nexters.android.pliary.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.FragmentMainBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.*
import javax.inject.Inject

class MainFragment : DaggerFragment() {
    @Inject
    lateinit var binding: FragmentMainBinding

    lateinit var job : Job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = binding.root
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.run{
            addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        job = GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}