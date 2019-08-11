package com.nexters.android.pliary.view.detail.bottom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.detail.DetailViewModel
import com.nexters.android.pliary.view.detail.bottom.adapter.DetailViewPageAdapter
import com.nexters.android.pliary.view.detail.calendar.fragment.DetailCalendarFragment
import com.nexters.android.pliary.view.detail.diary.fragment.DetailDiaryFragment
import kotlinx.android.synthetic.main.fragment_detail_bottom.*

class DetailBottomFragment : BaseFragment<DetailViewModel>() {

    companion object{
        const val TAB_DIARY = 0
        const val TAB_CALENDAR = 1
    }

    private var currentTab =
        TAB_DIARY


    override fun getModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_bottom, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
    }

    private fun initViewPager() {
        val vpAdatper = fragmentManager?.let {
            DetailViewPageAdapter(it, lifecycle)
        }?.apply {
            addFragment(DetailDiaryFragment())
            addFragment(DetailCalendarFragment())
        }
        vpPage.adapter = vpAdatper

        initTabView()
    }

    private fun initTabView() {
        val mediator = TabLayoutMediator(tabLayout, vpPage,
            TabLayoutMediator.OnConfigureTabCallback { tab, position ->
                // Styling each tab here
                val tabTextList = resources.getStringArray(R.array.detail_tab)
                val tabLinear = LayoutInflater.from(context).inflate(R.layout.tab_custom_item, null) as LinearLayout
                val tabTitle = tabLinear.findViewById<TextView>(R.id.tvTabTitle)
                tabTitle.text = tabTextList[position]
                tab.customView = tabLinear
            })

        tabLayout.getTabAt(0)?.setTabTitleBold(true)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.setTabTitleBold(false)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                currentTab = tab?.position ?: 0
                tab?.setTabTitleBold(true)

                when(currentTab) {
                    TAB_DIARY -> {}
                    TAB_CALENDAR -> {}
                }
            }

        })

        mediator.attach()
    }

    private fun TabLayout.Tab.setTabTitleBold(isSelect: Boolean) {
        with(this.customView as LinearLayout) {
            val title = this.findViewById<TextView>(R.id.tvTabTitle)
            if(isSelect) {
                title.setTextAppearance(R.style.AndH4SerifBlackCenter20Gray1)
            } else {
                title.setTextAppearance(R.style.AndH4SerifRegularLeft20Gray5)
            }
        }

    }

}