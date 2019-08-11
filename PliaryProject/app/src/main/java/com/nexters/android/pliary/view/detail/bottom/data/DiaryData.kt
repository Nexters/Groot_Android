package com.nexters.android.pliary.view.detail.bottom.data

sealed class DiaryData {
    data class DateCount(
        val dateCount: Int,
        val nickName: String? = ""
    ) : DiaryData()
    data class DiaryItem(
        val id: Int,
        val writeDate: String,
        val imageUrl: String? = null,
        val content: String? = null
    ) : DiaryData()
}
