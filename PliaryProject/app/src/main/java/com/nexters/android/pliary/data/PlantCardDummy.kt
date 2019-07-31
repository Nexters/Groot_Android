package com.nexters.android.pliary.data

data class PlantCardDummy(
    val plantSpecies : String?,
    val plantName : String?,
    val plantNickname : String?,
    val plantDate : String?,
    val isWatered : Boolean = false,
    val plantImage : String?
)