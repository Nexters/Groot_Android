package com.nexters.android.pliary.view.detail.edit

import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Diary
import javax.inject.Inject

internal class DiaryViewerViewModel @Inject constructor(val localDataSource: LocalDataSource) : BaseViewModel() {

    lateinit var diaryData: Diary


}