package com.nexters.android.pliary.view.detail.diary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Diary
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

internal class DetailDiaryViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {

    private val _diaryList = MutableLiveData<Event<ArrayList<DiaryData>>>()
    val diaryList : LiveData<Event<ArrayList<DiaryData>>> get() = _diaryList

    var plantData : Plant? = null

    val list = arrayListOf<DiaryData>()

    fun reqDateCount(plant: Plant?) {
        plant?.let {
            plantData = plant
            list.add(0, DiaryData.DateCount(
            dateCount = 100, //plant.takeDate,
            nickName = it.nickName
            ))
        }
    }

    fun reqDiaryList(diaryList: List<Diary>) {
        diaryList.forEach {
            list.add(DiaryData.DiaryItem(
                id = it.id.toInt(),
                writeDate = DateTimeFormatter.ofPattern("YY.MM.dd").format(it.date),
                imageUrl = it.photoUrl,
                content = it.content
            ))
        }

        _diaryList.value = Event(list)
    }
}