package com.nexters.android.pliary.view.detail.edit

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Diary
import com.nexters.android.pliary.view.detail.diary.data.DiaryData.*
import com.nexters.android.pliary.view.util.toZonedDateTime
import com.nexters.android.pliary.view.util.today
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

internal class DiaryEditViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {

    private val _addPhotoEvent = SingleLiveEvent<Unit>()
    val addPhotoEvent : SingleLiveEvent<Unit>  get() = _addPhotoEvent

    private val _setPhotoView = SingleLiveEvent<String?>()
    val setPhotoView : SingleLiveEvent<String?> get() = _setPhotoView

    private val _clickDoneEvent = SingleLiveEvent<Unit>()
    val clickDoneEvent : SingleLiveEvent<Unit> get() = _clickDoneEvent

    val content = MutableLiveData<String>().apply { postValue("") }

    fun onClickAddPhoto(){
        _addPhotoEvent.call()
    }

    fun onSetPhotoView(path : String?){
        _setPhotoView.value = path
    }

    fun onClickDone(cardID: Long) {

        val diary = Diary(
            plantId = cardID,
            title = "",
            content = content.value,
            photoUrl = setPhotoView.value,
            date = ZonedDateTime.now()
        )
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.upsertDiaries(diary)
        }
        _clickDoneEvent.call()
    }
}