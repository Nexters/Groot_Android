package com.nexters.android.pliary.view.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.data.PlantCard
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Plant
import javax.inject.Inject

internal class HomeViewModel @Inject constructor(localDataSource: LocalDataSource) : BaseViewModel() {

    val liveList = localDataSource.plants()
    val currentPos = MutableLiveData<Int>().apply { postValue(-1) }
    // 리스트 초기화
    private val _listSetData = MutableLiveData<Event<ArrayList<PlantCard>>>()
    val listSetData: LiveData<Event<ArrayList<PlantCard>>> get() = _listSetData


    // 새 카드 등록
    private val _addCardEvent = SingleLiveEvent<Unit>()
    val addCardEvent: LiveData<Unit> get() = _addCardEvent

    // 카드 상세
    private val _cardDetailEvent = SingleLiveEvent<Pair<Int, ArrayList<Pair<View, String>?>>>()
    val cardDetailEvent: LiveData<Pair<Int, ArrayList<Pair<View, String>?>>> get() = _cardDetailEvent

    fun reqPlantCardData(local : List<Plant>) {

        val list = arrayListOf<PlantCard>()
        val item = local?.map { PlantCard.PlantCardItem(it) }
        item?.let { list.addAll(it) }
        list.add(PlantCard.EmptyCard())
        _listSetData.value = Event(list)

        //val local = localDataSource.plants().await()


        /*_listSetData.value = Event(arrayListOf<PlantCard>(
            PlantCard.PlantCardItem(
                plantSpecies = "Rose",
                plantName = "장미",
                plantNickname = "미정이",
                plantDate = "2019.08.02",
                isWatered = false,
                plantImage = null
            ), PlantCard.PlantCardItem(
                plantSpecies = "SumFlower",
                plantName = "해바라기",
                plantNickname = "해해",
                plantDate = "2019.08.02",
                isWatered = false,
                plantImage = null
            )
        ))*/
    }

    fun onClickAddCard() {
        _addCardEvent.call()
    }

    fun onClickCardDetail(cardId: Int, sharedElements: ArrayList<Pair<View, String>?>) {
        _cardDetailEvent.value = cardId to sharedElements
    }
}