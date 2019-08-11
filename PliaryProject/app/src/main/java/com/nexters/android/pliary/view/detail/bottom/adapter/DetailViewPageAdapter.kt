package com.nexters.android.pliary.view.detail.bottom.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class DetailViewPageAdapter(fragmentManager : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    var viewList = arrayListOf<Fragment>()

    override fun getItemCount(): Int = viewList.size

    override fun createFragment(position: Int): Fragment {
        return viewList[position]
    }

    fun addFragment(fragment: Fragment) {
        viewList.add(fragment)
    }
}