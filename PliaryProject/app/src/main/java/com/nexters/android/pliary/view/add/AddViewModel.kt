package com.nexters.android.pliary.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.data.PlantSpecies
import javax.inject.Inject

class AddViewModel @Inject constructor() : BaseViewModel() {

    val currentPlant = MutableLiveData<PlantSpecies>()

    private val _plantSelectEvent = MutableLiveData<Event<PlantSpecies>>()
    val plantSelectEvent : LiveData<Event<PlantSpecies>> get() = _plantSelectEvent

    fun onSelectPlant(plant: PlantSpecies) {
        _plantSelectEvent.value = Event(plant)
    }
}