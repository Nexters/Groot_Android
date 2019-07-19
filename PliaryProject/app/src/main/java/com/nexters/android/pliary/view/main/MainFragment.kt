package com.nexters.android.pliary.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.android.pliary.databinding.FragmentMainBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainFragment : DaggerFragment() {
    @Inject
    lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var txtHelloWorld: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = binding.root
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = txtHelloWorld
    }
}