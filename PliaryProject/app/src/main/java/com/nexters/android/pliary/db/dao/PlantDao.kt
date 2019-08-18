package com.nexters.android.pliary.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.nexters.android.pliary.db.entity.Plant


@Dao
internal abstract class PlantDao : BaseDao<Plant>() {
    @Query("SELECT * FROM plant")
    abstract fun get(): LiveData<List<Plant>>

    @Query("SELECT * FROM plant WHERE id = :id")
    abstract fun getById(id: Long): LiveData<Plant>

    @Query("DELETE FROM plant WHERE id= :id")
    abstract fun deleteById(id: Long)

    @Query("DELETE FROM plant")
    abstract fun deleteAll()
}