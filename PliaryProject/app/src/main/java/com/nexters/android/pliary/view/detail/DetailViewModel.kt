package com.nexters.android.pliary.view.detail

import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Plant
import javax.inject.Inject

internal class DetailViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {
    val cardLiveID = MutableLiveData<Long>().apply { postValue(0)}

    val plantLiveData = MutableLiveData<Plant>()
}