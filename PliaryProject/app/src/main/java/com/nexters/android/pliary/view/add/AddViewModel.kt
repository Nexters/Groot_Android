package com.nexters.android.pliary.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.data.PlantSpecies
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.converter.ZonedDateTimeConverter
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.util.toZonedDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

internal class AddViewModel @Inject constructor(private val localDataSource: LocalDataSource) : BaseViewModel() {

    val nickname = MutableLiveData<String>()
    val takeDate = MutableLiveData<String>()
    val lastWateredDate = MutableLiveData<String>()
    val waterTerm = MutableLiveData<String>()
    val enableDone = MediatorLiveData<Boolean>().apply {
        addSource(nickname) {
            postValue(!nickname.value.isNullOrBlank() && !takeDate.value.isNullOrBlank() && !lastWateredDate.value.isNullOrBlank() && !waterTerm.value.isNullOrBlank())
        }
        addSource(takeDate) {
            postValue(!nickname.value.isNullOrBlank() && !takeDate.value.isNullOrBlank() && !lastWateredDate.value.isNullOrBlank() && !waterTerm.value.isNullOrBlank())
        }
        addSource(lastWateredDate) {
            postValue(!nickname.value.isNullOrBlank() && !takeDate.value.isNullOrBlank() && !lastWateredDate.value.isNullOrBlank() && !waterTerm.value.isNullOrBlank())
        }
        addSource(waterTerm) {
            postValue(!nickname.value.isNullOrBlank() && !takeDate.value.isNullOrBlank() && !lastWateredDate.value.isNullOrBlank() && !waterTerm.value.isNullOrBlank())
        }
    }

    private val _plantSelectEvent = MutableLiveData<PlantSpecies>()
    val plantSelectEvent : LiveData<PlantSpecies> get() = _plantSelectEvent

    private val _plantDoneEvent = SingleLiveEvent<Unit>()
    val plantDoneEvent : LiveData<Unit> get() = _plantDoneEvent

    fun onSelectPlant(plant: PlantSpecies) {
        _plantSelectEvent.value = plant
    }

    fun onClickDone() {
        val plant = Plant(
            species = plantSelectEvent.value,
            nickName = nickname.value,
            takeDate = takeDate.value,
            lastWateredDate = lastWateredDate.value,
            waterTerm = waterTerm.value?.toInt()
        )

        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.upsertPlants(plant)
        }

        _plantDoneEvent.call()
    }
}