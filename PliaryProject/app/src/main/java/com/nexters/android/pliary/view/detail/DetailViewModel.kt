package com.nexters.android.pliary.view.detail

import androidx.lifecycle.LiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Plant
import javax.inject.Inject

internal class DetailViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {

    private val _liveData = SingleLiveEvent<Plant>()
    val liveData : LiveData<Plant> get() = _liveData

    fun getLivePlantData(id: Long) {
        val data = localDataSource.plant(id)
        _liveData.value = data
    }
}