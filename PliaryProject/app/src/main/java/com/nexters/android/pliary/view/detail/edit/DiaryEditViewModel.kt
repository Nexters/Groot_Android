package com.nexters.android.pliary.view.detail.edit

import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.SingleLiveEvent
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.converter.ZonedDateTimeConverter
import com.nexters.android.pliary.db.entity.Diary
import com.nexters.android.pliary.view.util.todayValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

internal class DiaryEditViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {

    var diaryData : Diary? = null

    private val _addPhotoEvent = SingleLiveEvent<Unit>()
    val addPhotoEvent : SingleLiveEvent<Unit>  get() = _addPhotoEvent

    private val _setPhotoView = SingleLiveEvent<String?>()
    val setPhotoView : SingleLiveEvent<String?> get() = _setPhotoView

    private val _clickDoneEvent = SingleLiveEvent<Unit>()
    val clickDoneEvent : SingleLiveEvent<Unit> get() = _clickDoneEvent

    val content = MutableLiveData<String>().apply { postValue("") }
    val writeDate = MutableLiveData<ZonedDateTime>().apply { postValue(ZonedDateTime.now(ZonedDateTimeConverter.ZONE_SEOUL)) }

    fun onClickAddPhoto(){
        _addPhotoEvent.call()
    }

    fun onSetPhotoView(path : String?){
        _setPhotoView.value = path
    }

    fun onClickDone(cardID: Long) {
        if(diaryData!=null) {
            diaryData?.let{
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.upsertDiaries(it.apply {
                        this.content = this@DiaryEditViewModel.content.value
                        this.photoUrl = this@DiaryEditViewModel.setPhotoView.value
                    })
                }
            }

        } else {
            val diary = Diary(
                plantId = cardID,
                content = content.value,
                photoUrl = setPhotoView.value,
                date = ZonedDateTime.now()
            )
            CoroutineScope(Dispatchers.IO).launch {
                localDataSource.upsertDiaries(diary)
            }
        }
        _clickDoneEvent.call()
    }
}