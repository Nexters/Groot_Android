package com.nexters.android.pliary.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nexters.android.pliary.data.PlantSpecies
import java.io.Serializable

@Entity(tableName = "plant")
internal data class Plant(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val species: PlantSpecies?,

    val nickName: String? = "",
    val takeDate: String?,
    val lastWateredDate: String?,
    val waterTerm: Int? = 1,

    val isWatered: Boolean = false
) : Serializable