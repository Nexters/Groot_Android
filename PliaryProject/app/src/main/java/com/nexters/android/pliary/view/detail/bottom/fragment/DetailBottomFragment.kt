package com.nexters.android.pliary.view.detail.bottom.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.android.pliary.R
import com.nexters.android.pliary.analytics.AnalyticsUtil
import com.nexters.android.pliary.analytics.FBEvents
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentDetailBottomBinding
import com.nexters.android.pliary.view.detail.DetailViewModel
import com.nexters.android.pliary.view.detail.bottom.adapter.DetailViewPageAdapter

internal class DetailBottomFragment : BaseFragment<DetailViewModel>() {
    private val TAG = this.toString()

    companion object{
        const val TAB_DIARY = 0
        const val TAB_CALENDAR = 1
    }

    private var currentTab = TAB_DIARY
    private val cardID : Long by lazy { arguments?.getLong("cardID") ?: 0L }
    private lateinit var binding : FragmentDetailBottomBinding
    private lateinit var vpAdapter : DetailViewPageAdapter

    private lateinit var mDetector: GestureDetectorCompat

    override fun getModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_bottom, container, false)
        return binding.root
        /*return if(::binding.isInitialized) {
            binding.root
        } else {
            //val view = inflater.inflate(R.layout.fragment_detail_bottom, container, false)
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_bottom, container, false)
            with(binding) {
                root
            }
        }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDetector = GestureDetectorCompat(context, object: GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onFling(
                event1: MotionEvent,
                event2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {

                Log.d("MyGestureListener", "onFling()")
                if (event1.action == MotionEvent.ACTION_DOWN && event1.y > (event2.y + 300)){
                    Log.d("MyGestureListener", "flinged up")
                }
                if (event1.action == MotionEvent.ACTION_DOWN && event2.y > (event1.y + 300)){
                    Log.d("MyGestureListener", "flinged down")
                    popBackStack()
                }
                return super.onFling(event1, event2, velocityX, velocityY)
            }

        })

        binding.root.setOnTouchListener { v, event ->
            Log.d(TAG, "setOnTouchListener: ${event.action}")
            mDetector.onTouchEvent(event)
        }

        initViewPager()

        binding.ivArrowUp.setOnClickListener { popBackStack() }
        binding.fbWrite.setOnClickListener {
            navigate(
                R.id.action_detailBottomFragment_to_diaryEditFragment,
                Bundle().apply { putLong("cardID", cardID) },
                null,
                null
            )
            AnalyticsUtil.event(FBEvents.DETAIL_WRITE_DIARY_CLICK)
        }

        viewModel.diaryViewEvent.observe(this, Observer {
            navigate(R.id.action_detailBottomFragment_to_diaryViewerFragment,
                Bundle().apply { putLong("diaryID", it) }
            )
        })

        viewModel.diaryModifyEvent.observe(this, Observer {
            navigate(R.id.action_detailBottomFragment_to_diaryEditFragment,
                Bundle().apply {
                    putLong("cardID", cardID)
                    putLong("diaryID", it)
                })
        })
    }

    private fun initViewPager() {
        vpAdapter = DetailViewPageAdapter(childFragmentManager, lifecycle).apply {
            addFragment()
        }
        binding.vpPage.adapter = vpAdapter

        initTabView()
    }

    private fun initTabView() {
        val mediator = TabLayoutMediator(binding.tabLayout, binding.vpPage) { tab, position ->
            // Styling each tab here
            val tabTextList = resources.getStringArray(R.array.detail_tab)
            val tabLinear = LayoutInflater.from(context).inflate(R.layout.tab_custom_item, null) as LinearLayout
            val tabTitle = tabLinear.findViewById<TextView>(R.id.tvTabTitle)
            tabTitle.text = tabTextList[position]
            tab.customView = tabLinear
        }


        binding.tabLayout.getTabAt(0)?.setTabTitleBold(true)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.setTabTitleBold(false)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                currentTab = tab?.position ?: 0
                tab?.setTabTitleBold(true)

                when(currentTab) {
                    TAB_DIARY -> { binding.fbWrite.isVisible = true }
                    TAB_CALENDAR -> { binding.fbWrite.isVisible = false }
                }
                AnalyticsUtil.event(FBEvents.DETAIL_SWIPE)
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