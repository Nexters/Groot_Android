package com.nexters.android.pliary.view.detail.diary.data

sealed class DiaryData(val type : Int, val listItemId: String) {
    companion object {
        const val DIARY_DATE = 0
        const val DIARY_ITEM = 1
    }

    data class DateCount(
        val dateCount: Int,
        val nickName: String? = ""
    ) : DiaryData(DIARY_DATE, this.toString())

    data class DiaryItem(
        val id: Int,
        val writeDate: String,
        val imageUrl: String? = null,
        val content: String? = null
    ) : DiaryData(DIARY_ITEM, this.toString())
}
