package com.nexters.android.pliary.db

import androidx.lifecycle.LiveData
import com.nexters.android.pliary.db.entity.Diary
import javax.inject.Inject

internal class LocalDataSourceImpl @Inject constructor(
    private val roomDatabase: PliaryRoomDatabase) : LocalDataSource {

    override fun diaries(): LiveData<List<Diary>> {
        return roomDatabase.diaryDao().get()
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