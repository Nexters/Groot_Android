package com.nexters.android.pliary.db

import androidx.lifecycle.LiveData
import com.nexters.android.pliary.db.entity.Diary

internal interface LocalDataSource {
    fun diaries(): LiveData<List<Diary>>
    fun diary(id: Long): LiveData<Diary>
    fun deleteDiary(id: Long)
    fun upsertDiaries(vararg diaries: Diary)
    fun deleteDiaries(vararg diaries: Diary)
    fun deleteAllDiaries()
}