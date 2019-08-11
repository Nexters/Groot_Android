package com.nexters.android.pliary.view.detail.bottom.data

data class DiaryData(
    val id: Int,
    val writeDate: String,
    val imageUrl: String? = null,
    val content: String? = null
)