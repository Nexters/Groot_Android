package com.nexters.android.pliary.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Plant
import javax.inject.Inject

internal class DetailViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {

    var plantCardData : LiveData<Plant>? = null


    private val _wateringEvent = SingleLiveEvent<Unit>()
    val wateringEvent get() = _wateringEvent

    private val _delayDateEvent = SingleLiveEvent<Int>()
    val delayDateEvent : LiveData<Int> get() = _delayDateEvent

    private val _menuEvent = SingleLiveEvent<Unit>()
    val menuEvent get() = _menuEvent

    fun getPlantCardData(id: Long) {
        plantCardData = localDataSource.plant(id)
    }

    fun onWateringPlant() {
        _wateringEvent.call()
    }

    fun onDelayWateringDate(delay: Int) {
        _delayDateEvent.value = delay
    }

    fun onClickMenu(){
        _menuEvent.call()
    }
}