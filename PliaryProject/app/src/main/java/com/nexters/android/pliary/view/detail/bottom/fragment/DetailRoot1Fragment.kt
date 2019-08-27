package com.nexters.android.pliary.view.detail.bottom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentDetailRootBinding
import com.nexters.android.pliary.view.detail.DetailViewModel

internal class DetailRoot1Fragment() : BaseFragment<DetailViewModel>() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentDetailRootBinding
    override fun getModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if(::binding.isInitialized) {
            binding.root
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_root, container, false)
            with(binding) {
                root
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val navHostFragment = childFragmentManager.findFragmentById(R.id.detailRootFragment) as NavHostFragment? ?: return
        navController = navHostFragment.navController*/
    }
}