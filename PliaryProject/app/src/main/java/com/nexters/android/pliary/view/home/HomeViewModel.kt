package com.nexters.android.pliary.view.home

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.data.PlantCard
import com.nexters.android.pliary.db.LocalDataSource
import javax.inject.Inject

internal class HomeViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
    ) : BaseViewModel() {

    // 리스트 초기화
    private val _listSetData = MutableLiveData<Event<ArrayList<PlantCard>>>()
    val listSetData: LiveData<Event<ArrayList<PlantCard>>> get() = _listSetData

    // 식물 종류
    private val _plantEngLive = SingleLiveEvent<String>()
    val plantEngLive : LiveData<String> get() = _plantEngLive
    private val _plantNicknameLive = SingleLiveEvent<String>()
    val plantNicknameLive : LiveData<String> get() = _plantNicknameLive
    private val _plantKorLive = SingleLiveEvent<String>()
    val plantKorLive : LiveData<String> get() = _plantKorLive


    // 새 카드 등록
    private val _addCardEvent = SingleLiveEvent<Unit>()
    val addCardEvent: LiveData<Unit> get() = _addCardEvent

    // 카드 상세
    private val _cardDetailEvent = SingleLiveEvent<Pair<Int, ArrayList<Pair<View, String>?>>>()
    val cardDetailEvent: LiveData<Pair<Int, ArrayList<Pair<View, String>?>>> get() = _cardDetailEvent

    fun reqPlantCardData() {
        val list = arrayListOf<PlantCard>()
        val local = localDataSource.plants().value?.map { PlantCard.PlantCardItem(it) }
        local?.let { list.addAll(it) }
        list.add(PlantCard.EmptyCard())
        _listSetData.value = Event(list)

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