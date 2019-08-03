package com.nexters.android.pliary.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.PagerSnapHelper
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.home.adapter.HomeCardAdapter
import com.nexters.android.pliary.view.util.LinePagerIndicatorDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel>() {

    @Inject
    lateinit var cardAdapter : HomeCardAdapter

    override fun getModelClass() = HomeViewModel::class.java

    private var cardIndicator : LinePagerIndicatorDecoration? = null

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
            initIndicatorDeco()
        })
        viewModel.addCardEvent.observe(this, Observer{ navigate(R.id.addFragment) })
    }
    private fun initRv() {
        rvCardList.apply{
            layoutManager = CardLayoutManager(context)//LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = cardAdapter
            setHasFixedSize(true)
            clearOnScrollListeners()
            onFlingListener = null
            PagerSnapHelper().attachToRecyclerView(this)
        }
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

}