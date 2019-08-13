package com.nexters.android.pliary.view.detail.edit

import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.SingleLiveEvent
import javax.inject.Inject

class DiaryEditViewModel @Inject constructor() : BaseViewModel() {

    private val _addPhotoEvent = SingleLiveEvent<Unit>()
    val addPhotoEvent : SingleLiveEvent<Unit>  get() = _addPhotoEvent

    private val _setPhotoView = SingleLiveEvent<String>()
    val setPhotoView : SingleLiveEvent<String> get() = _setPhotoView

    fun onClickAddPhoto(){
        _addPhotoEvent.call()
    }

    fun onSetPhotoView(path : String?){
        path?.let { _setPhotoView.value = it }
    }
}