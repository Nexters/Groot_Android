package com.nexters.android.pliary.view.detail.diary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.base.Event
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Diary
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import javax.inject.Inject

internal class DetailDiaryViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {

    private val _diaryList = MutableLiveData<Event<ArrayList<DiaryData>>>()
    val diaryList : LiveData<Event<ArrayList<DiaryData>>> get() = _diaryList

    fun reqDiaryList(plant: Plant?, diaryList: List<Diary>) {
        plant?.let {
            val list = arrayListOf<DiaryData>()
            list.add(DiaryData.DateCount(
                dateCount = 100, //plant.takeDate,
                nickName = it.nickName
            ))
            diaryList.forEach { d ->
                list.add(DiaryData.DiaryItem(
                    id = d.id.toInt(),
                    writeDate = d.date.toString(),
                    imageUrl = d.photoUrl,
                    content = d.content
                ))
            }

            _diaryList.value = Event(list)
        }

    }
}