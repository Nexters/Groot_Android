package com.nexters.android.pliary.db

import androidx.lifecycle.LiveData
import com.nexters.android.pliary.db.entity.Diary
import com.nexters.android.pliary.db.entity.Plant

internal interface LocalDataSource {
    fun plants(): LiveData<List<Plant>>
    fun plant(id: Long): LiveData<Plant>
    fun deletePlant(id: Long)
    fun upsertPlants(vararg plants: Plant)
    fun deletePlants(vararg plants: Plant)
    fun deleteAllPlants()

    fun diaries(plantId: Long) : LiveData<List<Diary>>
    fun diary(id: Long): LiveData<Diary>
    fun deleteDiary(id: Long)
    fun upsertDiaries(vararg diaries: Diary)
    fun deleteDiaries(vararg diaries: Diary)
    fun deleteAllDiaries()
}