package com.nexters.android.pliary.view.detail.edit

import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.view.detail.diary.data.DiaryData.*
import com.nexters.android.pliary.view.util.today
import javax.inject.Inject

class DiaryEditViewModel @Inject constructor() : BaseViewModel() {

    var uploadData : DiaryItem? = null

    private val _addPhotoEvent = SingleLiveEvent<Unit>()
    val addPhotoEvent : SingleLiveEvent<Unit>  get() = _addPhotoEvent

    private val _setPhotoView = SingleLiveEvent<String>()
    val setPhotoView : SingleLiveEvent<String> get() = _setPhotoView

    private val _clickDoneEvent = SingleLiveEvent<DiaryItem>()
    val clickDoneEvent : SingleLiveEvent<DiaryItem> get() = _clickDoneEvent

    private val _editDiaryContents = MutableLiveData<Event<String>>()
    val editDiaryContents : MutableLiveData<Event<String>> get() = _editDiaryContents

    fun onClickAddPhoto(){
        _addPhotoEvent.call()
    }

    fun onSetPhotoView(path : String?){
        path?.let { _setPhotoView.value = it }
    }

    fun onClickDone(contents: String, imgUrl: String) {
        uploadData = DiaryItem(
            content = contents,
            imageUrl = imgUrl,
            writeDate = today(),
            id = 0
        )
        _clickDoneEvent.value = uploadData
    }
}