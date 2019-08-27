package com.nexters.android.pliary.view.detail.bottom.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.android.pliary.view.detail.bottom.fragment.DetailRoot1Fragment
import com.nexters.android.pliary.view.detail.bottom.fragment.DetailRoot2Fragment


class DetailViewPageAdapter(fragmentManager : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    var viewList = SparseArray<Fragment>()

    override fun getItemCount(): Int = viewList.size()

    override fun createFragment(position: Int): Fragment {
        return viewList[position]
    }

    fun addFragment() {
        viewList.put(0, DetailRoot1Fragment())
        viewList.put(1, DetailRoot2Fragment())
    }
}