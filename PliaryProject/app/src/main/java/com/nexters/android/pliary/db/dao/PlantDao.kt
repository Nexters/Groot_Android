package com.nexters.android.pliary.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.room.Dao
import androidx.room.InvalidationTracker
import androidx.room.Query
import com.nexters.android.pliary.db.entity.Plant


@Dao
internal abstract class PlantDao : BaseDao<Plant>() {
    @Query("SELECT * FROM plant")
    abstract fun get(): LiveData<List<Plant>>

    @Query("SELECT * FROM plant WHERE id = :id")
    protected abstract fun getById(id: Long): LiveData<Plant>

    fun getDistinctUserById(id: Long): LiveData<Plant> = getById(id).getDistinct()

    @Query("DELETE FROM plant WHERE id= :id")
    abstract fun deleteById(id: Long)

    @Query("DELETE FROM plant")
    abstract fun deleteAll()
}

fun <T> LiveData<T>.getDistinct(): LiveData<T> {
    val distinctLiveData = MediatorLiveData<T>()
    distinctLiveData.addSource(this, object : Observer<T> {
        private var initialized = false
        private var lastObj: T? = null
        override fun onChanged(obj: T?) {
            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if ((obj == null && lastObj != null)
                || obj != lastObj
            ) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}
