package com.nexters.android.pliary.data


sealed class PlantCard(val type : Int) {
    companion object {
        const val EMPTY_CARD = 0
        const val PLANT_CARD = 1
    }

    data class PlantCardDummy(
        val plantSpecies : String?,
        val plantName : String?,
        val plantNickname : String?,
        val plantDate : String?,
        val isWatered : Boolean = false,
        val plantImage : String?
    ) : PlantCard(PLANT_CARD)

    class EmptyCard : PlantCard(EMPTY_CARD)
}
