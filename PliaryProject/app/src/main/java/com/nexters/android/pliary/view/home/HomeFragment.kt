package com.nexters.android.pliary.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.data.PlantCard
import com.nexters.android.pliary.databinding.FragmentHomeBinding
import com.nexters.android.pliary.db.dao.PlantDao_Impl
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
    private lateinit var binding: FragmentHomeBinding

    private val cardList = arrayListOf<PlantCard>()
    private var currentPosition = 0

    private var cardIndicator : LinePagerIndicatorDecoration? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if(::binding.isInitialized) {
            binding.root
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
            with(binding) {
                root
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //cardList.isEmpty()
        initObserver()
        initRv()

    }

    private fun initObserver() {
        viewModel.liveList.observe(this, Observer {
            viewModel.reqPlantCardData(it)
        })
        viewModel.listSetData.observe(this, eventObserver {
            cardList.clear()
            cardList.addAll(it)

            onScrollCard(it[currentPosition])

            cardAdapter.submitList(it)
            initIndicatorDeco()
        })
        viewModel.cardDetailEvent.observe(this, Observer {
            it.second.add(binding.plantNameLayout to getString(com.nexters.android.pliary.R.string.trans_detail))
            val extras = FragmentNavigator.Extras.Builder().apply {
                it.second.filterNotNull().forEach { (view, name) ->
                    addSharedElement(view, name)
                }
            }.build()

            navigate(
                R.id.action_homeFragment_to_detailFragment,
                Bundle().apply { putLong("cardID", it.first) }, // Bundle of args
                null, // NavOptions
                extras)
        })
        viewModel.addCardEvent.observe(this, Observer{ navigate(R.id.action_homeFragment_to_addFragment) })
    }

    private fun initRv() {
        binding.rvCardList.apply{
            layoutManager = CardLayoutManager(context)
            adapter = cardAdapter
            setHasFixedSize(true)
            clearOnScrollListeners()
            onFlingListener = null
            PagerSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val manager = recyclerView.layoutManager
                    var visibleFirstPos = -1
                    var visibleEndPos = -1

                    if (manager is LinearLayoutManager) {
                        visibleFirstPos = manager.findFirstVisibleItemPosition()
                        visibleEndPos = manager.findLastVisibleItemPosition()

                        currentPosition = when {
                            visibleFirstPos == 0 -> visibleFirstPos
                            visibleEndPos == cardList.lastIndex -> visibleEndPos
                            else -> (visibleFirstPos + visibleEndPos) / 2
                        }
                        onScrollCard(cardList[currentPosition])
                    }
                }
            })
        }
        cardAdapter.setCallbacks(object : HomeCardAdapter.Callbacks {
            override fun onClickCardDetail(sharedElements: ArrayList<Pair<View, String>?>, position: Int) {
                cardList[position].apply {
                    if(this is PlantCard.PlantCardItem) {
                        val id = this.plant.id
                        viewModel.onClickCardDetail(id, sharedElements)
                    }
                }
            }

            override fun onClickAddCard() {
                viewModel.onClickAddCard()
            }
        })
        //prepareTransitions()
    }

    fun onScrollCard(card: PlantCard) {
        when(card) {
            is PlantCard.PlantCardItem -> {
                binding.apply {
                    tvPlantName.text = card.plant.species?.name
                    tvNickname.text = card.plant.nickName
                    tvSpecies.text = card.plant.species?.nameKr
                    border.isVisible = true
                }
            }
            is PlantCard.EmptyCard -> {
                context?.let {
                    binding.apply {
                        tvPlantName.text = it.getString(R.string.home_add_plant)
                        tvNickname.text = it.getString(R.string.home_add_plant_msg)
                        tvSpecies.text = ""
                        border.isVisible = false
                    }
                }
            }
        }
    }

    private fun initIndicatorDeco() {
        cardIndicator?.apply {
            binding.rvCardList.removeItemDecoration(this)
        }
        setRvIndicator()
    }

    private fun setRvIndicator(){
        context?.run {
            if(cardAdapter.itemCount > 1) {
                LinePagerIndicatorDecoration(this, cardAdapter.itemCount).run {
                    cardIndicator = this
                    binding.rvCardList.addItemDecoration(this)
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