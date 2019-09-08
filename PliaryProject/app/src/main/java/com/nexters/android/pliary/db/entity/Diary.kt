package com.nexters.android.pliary.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "diary")
internal data class Diary(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val plantId: Long = 0,

    var content: String?,
    var photoUrl: String?,

    val date: ZonedDateTime
)