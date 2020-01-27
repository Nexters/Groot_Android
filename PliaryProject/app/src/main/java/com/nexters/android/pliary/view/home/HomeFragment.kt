package com.nexters.android.pliary.view.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.SharedElementCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.data.PlantCard
import com.nexters.android.pliary.data.getLocalImage
import com.nexters.android.pliary.data.toUIData
import com.nexters.android.pliary.databinding.FragmentHomeBinding
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.notification.AlarmBroadcastReceiver
import com.nexters.android.pliary.view.home.adapter.HomeCardAdapter
import com.nexters.android.pliary.view.util.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.plant_card_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


internal class HomeFragment : BaseFragment<HomeViewModel>() {
    val TAG = this::class.java.simpleName

    @Inject
    lateinit var cardAdapter : HomeCardAdapter

    override fun getModelClass() = HomeViewModel::class.java
    private lateinit var binding: FragmentHomeBinding

    private val cardList = arrayListOf<PlantCard>()
    private var currentPosition = 0
    private var plantData : Plant? = null

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
        activity?.window?.run{
            clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

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
            initIndicatorDeco(it)
        })
        viewModel.cardDetailEvent.observe(this, Observer {
            it.second.add(binding.plantNameLayout to getString(R.string.trans_detail))
            val extras = FragmentNavigator.Extras.Builder().apply {
                it.second.filterNotNull().forEach { (view, name) ->
                    addSharedElement(view, name)
                }
            }.build()

            navigate(
                R.id.action_homeFragment_to_detailFragment,
                Bundle().apply {
                    putLong("cardID", it.first.cardID)
                    putInt("defaultImage", it.first.defaultImage)
                }, // Bundle of args
                null, // NavOptions
                extras)
        })
        viewModel.addCardEvent.observe(this, Observer{
            if(cardList.count() <= 5) {
                navigate(R.id.action_homeFragment_to_addFragment)
            } else {
                Toast.makeText(context, getString(R.string.home_plant_count_over), Toast.LENGTH_SHORT).show()
            }
        })

        cardAdapter.plantVM.plantID.observe(this, Observer { getPlantData(it) })

        cardAdapter.plantVM.wateringEvent.observe(this, Observer {
            plantData?.let {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    cardAdapter.plantVM.localDataSource.upsertPlants(it.apply {
                        lastWateredDate = todayValue()
                        val set = wateredDays.toHashSet()
                        set.add(todayValue())
                        val result = arrayListOf<String>()
                        set.forEach { result.add(it) }
                        wateredDays = result
                        willbeWateringDate = todayValue().getFutureWateringDate(it.waterTerm ?: 1)
                    })
                }
                if(job.isCompleted) registAlarm(it.willbeWateringDate, it.nickName ?: "", it.id.toInt())
            }

        })

        cardAdapter.plantVM.delayDateEvent.observe(this, Observer {delay ->
            plantData?.let {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    cardAdapter.plantVM.localDataSource.upsertPlants(it.apply { willbeWateringDate = willbeWateringDate.getFutureWateringDate(delay) })
                }
                if(job.isCompleted) registAlarm(it.willbeWateringDate, it.nickName ?: "", it.id.toInt())
            }
        })
    }

    private fun getPlantData(id : Long) {
        cardAdapter.plantVM.localDataSource.plant(id).observe(this, Observer { plantData = it })
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
                        val ui = this.plant.toUIData()
                        viewModel.onClickCardDetail(
                            IntoDetailInfo(id, ui.photoUrl?.getLocalImage(!ui.isDayPast) ?: 0),
                            sharedElements
                        )
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

    private fun initIndicatorDeco(list: ArrayList<PlantCard>) {
        cardIndicator?.apply {
            binding.rvCardList.removeItemDecoration(this)
        }
        setRvIndicator(list)
    }

    private fun setRvIndicator(list: ArrayList<PlantCard>){
        context?.run {
            LinePagerIndicatorDecoration(this, list.count()).run {
                cardIndicator = this
                binding.rvCardList.addItemDecoration(this)
            }
        }
        binding.rvCardList.scrollToPosition(currentPosition)
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

    private fun registAlarm(alarmDate: String, nickname: String, id: Int) {

        val am = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(AlarmBroadcastReceiver.NOTIFICATION_TITLE, getString(R.string.noti_title))
            putExtra(AlarmBroadcastReceiver.NOTIFICATION_CONTENT, getString(R.string.noti_message, nickname))
            putExtra(AlarmBroadcastReceiver.NOTIFICATION_ID, id)
        }

        val sender = PendingIntent.getBroadcast(context, id, intent, 0)

        val calendar = Calendar.getInstance()
        //알람시간 calendar에 set해주기
        val dateFormat = SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss.SSSX", Locale.KOREA)
        calendar.time = dateFormat.parse("${alarmDate}T08:00:35.741+09")

        //알람 예약

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, sender)
        } else {
            am.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, sender)
        }
    }


}