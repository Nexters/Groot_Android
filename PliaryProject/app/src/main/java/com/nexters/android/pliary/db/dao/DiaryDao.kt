package com.nexters.android.pliary.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.nexters.android.pliary.db.entity.Diary

@Dao
internal abstract class DiaryDao : BaseDao<Diary>() {
    @Query("SELECT * FROM diary WHERE id = :id")
    abstract fun getById(id: Long): LiveData<Diary>

    @Query("SELECT * FROM diary WHERE plantId = :plantId")
    abstract fun get(plantId: Long): LiveData<List<Diary>>

    @Query("DELETE FROM diary WHERE id = :id")
    abstract fun deleteById(id: Long)

    @Query("DELETE FROM diary")
    abstract fun deleteAll()
}