package com.nexters.android.pliary.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.home.adapter.HomeCardAdapter
import com.nexters.android.pliary.view.util.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel>() {

    @Inject
    lateinit var cardAdapter : HomeCardAdapter

    override fun getModelClass() = HomeViewModel::class.java

    private var cardIndicator : LinePagerIndicatorDecoration? = null
    private val rvHeight : Int by lazy { ((context?.resources?.displayMetrics?.run { heightPixels } ?: 0) * 0.65).toInt() }
    private val cardItemDecoList by lazy {
        listOf(
            Decorations.startOffset(context?.dpToPx(46) ?: 0),
            Decorations.endOffset(context?.dpToPx(26) ?: 0),
            Decorations.itemSpacing(rightPx = context?.dpToPx(20) ?: 0, topPx = context?.dpToPx(0) ?: 0))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.reqPlantCardData()
        initObserver()

    }

    private fun initObserver() {
        viewModel.listSetData.observe(this, Observer{
            cardAdapter.setCardList(it)
            cardAdapter.setCallbacks(object : HomeCardAdapter.Callbacks {
                override fun onClickAddCard() {
                    viewModel.onClickAddCard()
                }
            })
            initRv()
            setRvDeco()
            initIndicatorDeco()
        })
        viewModel.addCardEvent.observe(this, Observer{
            findNavController().navigate(R.id.addFragment)
        })
    }
    private fun initRv() {
        setRvHeight()
        rvCardList.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = cardAdapter
            setHasFixedSize(true)
            clearOnScrollListeners()
            onFlingListener = null
            PagerSnapHelper().attachToRecyclerView(rvCardList)
        }
    }

    private fun setRvDeco() {
        rvCardList?.apply {cardItemDecoList.forEach { removeItemDecoration(it) }}
        cardItemDecoList.forEach{ rvCardList.addItemDecoration(it) }
    }

    private fun initIndicatorDeco() {
        cardIndicator?.apply {
            rvCardList.removeItemDecoration(this)
        }
        setRvIndicator()
    }

    private fun setRvIndicator(){
        context?.run {
            if(cardAdapter.itemCount > 1) {
                LinePagerIndicatorDecoration(this, cardAdapter.itemCount).run {
                    cardIndicator = this
                    rvCardList.addItemDecoration(this)
                }
            }
        }
    }

    private fun setRvHeight() {
        (rvCardList.layoutParams as LinearLayout.LayoutParams).run {
            width = LinearLayout.LayoutParams.MATCH_PARENT
            height =  rvHeight
        }
    }

}