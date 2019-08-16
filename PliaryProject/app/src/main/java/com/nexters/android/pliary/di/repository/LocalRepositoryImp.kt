package com.nexters.android.pliary.di.repository

import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import kotlinx.coroutines.*

class LocalRepositoryImp : LocalRepository {

    val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)
    override fun writeDiary(diary: DiaryData.DiaryItem): Deferred<Unit> {
        return scope.async {  }
    }
}