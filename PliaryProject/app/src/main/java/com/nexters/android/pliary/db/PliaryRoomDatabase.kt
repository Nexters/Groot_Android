package com.nexters.android.pliary.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nexters.android.pliary.db.converter.ListConverter
import com.nexters.android.pliary.db.converter.PlantConverter
import com.nexters.android.pliary.db.converter.ZonedDateTimeConverter
import com.nexters.android.pliary.db.dao.DiaryDao
import com.nexters.android.pliary.db.dao.PlantDao
import com.nexters.android.pliary.db.entity.Diary
import com.nexters.android.pliary.db.entity.Plant

@Database(
    entities = [
        Diary::class,
        Plant::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(
    ZonedDateTimeConverter::class,
    PlantConverter::class,
    ListConverter::class
)
internal abstract class PliaryRoomDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun plantDao(): PlantDao
}