package com.nexters.android.pliary.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.nexters.android.pliary.R
import com.nexters.android.pliary.data.PlantCardDummy
import com.nexters.android.pliary.view.home.adapter.HomeCardAdapter
import com.nexters.android.pliary.view.util.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    private var cardIndicator : LinePagerIndicatorDecoration? = null

    @Inject
    lateinit var cardAdapter : HomeCardAdapter

    private val cardItemDecoList by lazy {
        listOf(
            Decorations.startOffset(context?.dpToPx(35) ?: 0),
            Decorations.endOffset(context?.dpToPx(20) ?: 0),
            Decorations.itemSpacing(rightPx = context?.dpToPx(15) ?: 0, topPx = context?.dpToPx(0) ?: 0))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        setRvDeco()
        initIndicatorDeco()
    }

    private fun initRv() {
        cardAdapter.cardList = arrayListOf<PlantCardDummy>(PlantCardDummy(
            plantSpecies = "Rose",
            plantName = "장미",
            plantNickname = "미정이",
            plantDate = "2019.08.02",
            isWatered = false,
            plantImage = null
        ))

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
                LinePagerIndicatorDecoration(this, cardAdapter.cardList.size).run {
                    cardIndicator = this
                    rvCardList.addItemDecoration(this)
                }
            }
        }
    }

}