package com.nexters.android.pliary.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nexters.android.pliary.db.dao.DiaryDao
import com.nexters.android.pliary.db.dao.PlantDao
import com.nexters.android.pliary.db.entity.Diary
import com.nexters.android.pliary.db.entity.Plant

@Database(
    entities = [
        Diary::class,
        Plant::class
    ],
    version = 1
)
@TypeConverters(
    ZonedDateTimeConverter::class
)
internal abstract class PliaryRoomDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun plantDao(): PlantDao
}