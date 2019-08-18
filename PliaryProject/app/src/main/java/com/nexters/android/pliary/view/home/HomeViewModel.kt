package com.nexters.android.pliary.view.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.FragmentNavigator
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.data.PlantCard
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    // 리스트 초기화
    private val _listSetData = MutableLiveData<Event<ArrayList<PlantCard>>>()
    val listSetData: LiveData<Event<ArrayList<PlantCard>>> get() = _listSetData

    // 새 카드 등록
    private val _addCardEvent = SingleLiveEvent<Unit>()
    val addCardEvent: LiveData<Unit> get() = _addCardEvent

    // 카드 상세
    private val _cardDetailEvent = SingleLiveEvent<Pair<Int, ArrayList<Pair<View, String>?>>>()
    val cardDetailEvent: LiveData<Pair<Int, ArrayList<Pair<View, String>?>>> get() = _cardDetailEvent

    fun reqPlantCardData() {
        _listSetData.value = Event(arrayListOf<PlantCard>(
            PlantCard.PlantCardDummy(
                plantSpecies = "Rose",
                plantName = "장미",
                plantNickname = "미정이",
                plantDate = "2019.08.02",
                isWatered = false,
                plantImage = null
            ), PlantCard.PlantCardDummy(
                plantSpecies = "SumFlower",
                plantName = "해바라기",
                plantNickname = "해해",
                plantDate = "2019.08.02",
                isWatered = false,
                plantImage = null
            )
        ))
    }

    fun onClickAddCard() {
        _addCardEvent.call()
    }

    fun onClickCardDetail(cardId: Int, sharedElements: ArrayList<Pair<View, String>?>) {
        _cardDetailEvent.value = cardId to sharedElements
    }
}