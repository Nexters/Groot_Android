package com.nexters.android.pliary.di.repository

import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.Deferred

internal interface LocalRepository {
    fun writeDiary(diary : DiaryData.DiaryItem) : Deferred<Unit>
    /*fun diary(id: Long): Flowable<Diary>
    fun diaries(): Flowable<List<Diary>>
    fun upsertDiaries(vararg diaries: Diary): Completable
    fun deleteDiaries(vararg diaries: Diary): Completable
    fun deleteDiary(diary: Diary): Completable
    fun setPrivacyAgree(agree: Boolean): Completable
    fun getPrivacyAgree(): Observable<Boolean>

    fun scoreItems(): Observable<List<ScoreItem>>
    fun boardItems(): Observable<List<BoardItem>>*/
}