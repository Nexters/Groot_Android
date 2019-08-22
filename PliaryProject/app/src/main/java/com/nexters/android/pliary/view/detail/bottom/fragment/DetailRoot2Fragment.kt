package com.nexters.android.pliary.view.detail.bottom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.detail.diary.viewmodel.DetailDiaryViewModel

internal class DetailRoot2Fragment : BaseFragment<DetailDiaryViewModel>() {
    private lateinit var navController: NavController

    override fun getModelClass(): Class<DetailDiaryViewModel> = DetailDiaryViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_root2, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.detailRoot2Fragment) as NavHostFragment? ?: return
        navController = navHostFragment.navController
    }
}