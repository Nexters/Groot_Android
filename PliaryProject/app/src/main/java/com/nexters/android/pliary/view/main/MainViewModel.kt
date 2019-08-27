package com.nexters.android.pliary.view.main

import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.db.entity.Plant

internal class MainViewModel : BaseViewModel() {
    var cardLiveID = 0L

    lateinit var plantLiveData : Plant
}