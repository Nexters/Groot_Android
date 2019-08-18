package com.nexters.android.pliary.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.LocalDataSourceImpl
import com.nexters.android.pliary.db.PliaryRoomDatabase
import com.nexters.android.pliary.db.dao.DiaryDao
import com.nexters.android.pliary.db.dao.PlantDao
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import javax.inject.Named


@Module
internal class DBModule {

    @Provides
    @Singleton
    fun provideDiaryDao(db: PliaryRoomDatabase): DiaryDao {
        return db.diaryDao()
    }

    @Provides
    @Singleton
    fun providePlantDao(db: PliaryRoomDatabase): PlantDao {
        return db.plantDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(db: PliaryRoomDatabase): LocalDataSourceImpl {
        return LocalDataSourceImpl(db)
    }

    @Provides
    @Singleton
    fun providePliaryRoomDatabase(@Named("appContext") context: Context): PliaryRoomDatabase {
        return createRoomDatabase(context, PliaryRoomDatabase::class.java, "pliary-db")
    }
}

internal inline fun <reified T : RoomDatabase> createRoomDatabase(context: Context, clazz: Class<T>, dbName: String): T {
    return Room.databaseBuilder(context, clazz, dbName)
        .fallbackToDestructiveMigration()
        .build()
}