package com.nexters.android.pliary.view.detail.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Plant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


internal class ModifyViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {

    lateinit var plantData : Plant
    val nickname = MutableLiveData<String>()
    val waterTerm = MutableLiveData<String>()
    val enableDone = MediatorLiveData<Boolean>().apply {
        addSource(nickname) {
            postValue(!nickname.value.isNullOrBlank() && !waterTerm.value.isNullOrBlank())
        }
        addSource(waterTerm) {
            postValue(!nickname.value.isNullOrBlank() && !waterTerm.value.isNullOrBlank())
        }
    }
    private val _plantDoneEvent = SingleLiveEvent<Unit>()
    val plantDoneEvent : LiveData<Unit> get() = _plantDoneEvent

    fun onClickDone() {
        when(enableDone.value) {
            true -> {
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.upsertPlants(plantData.apply {
                        this.waterTerm = this@ModifyViewModel.waterTerm.value?.toInt()
                        this.nickName = this@ModifyViewModel.nickname.value
                    })
                }
                _plantDoneEvent.call()

            }
            else -> null
        }
    }

}