package com.nexters.android.pliary.db

import androidx.lifecycle.LiveData
import com.nexters.android.pliary.db.entity.Diary
import com.nexters.android.pliary.db.entity.Plant
import javax.inject.Inject

internal class LocalDataSourceImpl @Inject constructor(
    private val roomDatabase: PliaryRoomDatabase) : LocalDataSource {

    override fun plants(): LiveData<List<Plant>> {
        return roomDatabase.plantDao().get()
    }

    override fun plant(id: Long): LiveData<Plant> {
        return roomDatabase.plantDao().getDistinctUserById(id)
    }

    override fun deletePlant(id: Long) {
        return roomDatabase.plantDao().deleteById(id)
    }

    override fun upsertPlants(vararg plants: Plant) {
        return roomDatabase.plantDao().upserts(*plants)
    }

    override fun deletePlants(vararg plants: Plant) {
        roomDatabase.plantDao().deletes(*plants)
    }

    override fun deleteAllPlants() {
        roomDatabase.plantDao().deleteAll()
    }

    override fun diaries(plantId: Long): LiveData<List<Diary>> {
        return roomDatabase.diaryDao().get(plantId)
    }

    override fun diary(id: Long): LiveData<Diary> {
        return roomDatabase.diaryDao().getById(id)
    }

    override fun deleteDiary(id: Long) {
        return roomDatabase.diaryDao().deleteById(id)
    }

    override fun upsertDiaries(vararg diaries: Diary) {
        return roomDatabase.diaryDao().upserts(*diaries)
    }

    override fun deleteDiaries(vararg diaries: Diary) {
        roomDatabase.diaryDao().deletes(*diaries)
    }

    override fun deleteAllDiaries() {
        roomDatabase.diaryDao().deleteAll()
    }
}