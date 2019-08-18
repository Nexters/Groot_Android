package com.nexters.android.pliary.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.data.PlantCard
import com.nexters.android.pliary.view.home.adapter.HomeCardAdapter
import com.nexters.android.pliary.view.util.CardLayoutManager
import com.nexters.android.pliary.view.util.LinePagerIndicatorDecoration
import com.nexters.android.pliary.view.util.eventObserver
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.plant_card_item.view.*
import javax.inject.Inject

internal class HomeFragment : BaseFragment<HomeViewModel>() {

    @Inject
    lateinit var cardAdapter : HomeCardAdapter

    override fun getModelClass() = HomeViewModel::class.java

    private val cardList = arrayListOf<PlantCard>()

    private var cardIndicator : LinePagerIndicatorDecoration? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

    }

    private fun initObserver() {
        viewModel.liveList.observe(this, Observer {
            viewModel.reqPlantCardData(it)
        })
        viewModel.listSetData.observe(this, eventObserver {
            cardList.clear()
            cardList.addAll(it)

            /*val list = arrayListOf<PlantCard>()
            it?.let { list.addAll(it) }
            list.add(PlantCard.EmptyCard())*/

            cardAdapter.submitList(it)
            //cardAdapter.setCardList(it)
            cardAdapter.setCallbacks(object : HomeCardAdapter.Callbacks {
                override fun onClickCardDetail(sharedElements: ArrayList<Pair<View, String>?>) {
                    viewModel.onClickCardDetail(0, sharedElements)
                }

                override fun onClickAddCard() {
                    viewModel.onClickAddCard()
                }
            })
            initRv()
            initIndicatorDeco()
        })
        viewModel.cardDetailEvent.observe(this, Observer {
            it.second.add(plantNameLayout to getString(R.string.trans_detail))
            val extras = FragmentNavigator.Extras.Builder().apply {
                it.second.filterNotNull().forEach { (view, name) ->
                    addSharedElement(view, name)
                }
            }.build()
            navigate(R.id.action_homeFragment_to_detailFragment,
                null, // Bundle of args
                null, // NavOptions
                extras)
        })
        viewModel.addCardEvent.observe(this, Observer{ navigate(R.id.action_homeFragment_to_addFragment) })
    }

    private fun initRv() {
        rvCardList.apply{
            layoutManager = CardLayoutManager(context)
            adapter = cardAdapter
            setHasFixedSize(true)
            clearOnScrollListeners()
            onFlingListener = null
            PagerSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    onSwipCard(cardList[position])
                }
            })
        }
        //prepareTransitions()
    }

    fun onSwipCard(card: PlantCard) {
        when(card) {
            is PlantCard.PlantCardItem -> {
                tvPlantName.text = card.plant.species?.name
                tvNickname.text = card.plant.nickName
                tvSpecies.text = card.plant.species?.nameKr
            }
            is PlantCard.EmptyCard -> {
                context?.apply {
                    tvPlantName.text = getString(R.string.home_add_plant)
                    tvNickname.text = getString(R.string.home_add_plant_msg)
                    tvSpecies.text = ""
                }
            }
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

    private fun prepareTransitions() {
        //exitTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {

                val position = (rvCardList.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                val selectedViewHolder = rvCardList.findViewHolderForAdapterPosition(position)

                if (selectedViewHolder?.itemView == null) {
                    return
                }

                // Map the first shared element name to the child ImageView.
                sharedElements?.apply {
                    names?.let {
                        put(it[0], selectedViewHolder.itemView.ivPlant)
                    }

                }
            }
        })

    }

}