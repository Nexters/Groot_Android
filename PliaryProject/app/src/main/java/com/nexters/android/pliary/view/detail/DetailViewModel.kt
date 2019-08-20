package com.nexters.android.pliary.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Plant
import javax.inject.Inject

internal class DetailViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {
}