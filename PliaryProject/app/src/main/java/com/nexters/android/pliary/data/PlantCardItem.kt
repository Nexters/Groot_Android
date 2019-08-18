package com.nexters.android.pliary.data

import com.nexters.android.pliary.db.entity.Plant


internal sealed class PlantCard(val type : Int, val listItemId: String) {
    companion object {
        const val EMPTY_CARD = 0
        const val PLANT_CARD = 1
    }

    data class PlantCardItem(val plant: Plant) : PlantCard(PLANT_CARD, plant.toString())

    class EmptyCard : PlantCard(EMPTY_CARD, this.toString())
}
