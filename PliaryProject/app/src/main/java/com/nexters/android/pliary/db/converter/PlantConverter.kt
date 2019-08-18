package com.nexters.android.pliary.db.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.nexters.android.pliary.data.PlantSpecies

internal class PlantConverter {

    private val gson = GsonProvider.gson

    @TypeConverter
    fun fromPlant(value: PlantSpecies): String {
        val type = PlantSpecies::class.java
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromPlantList(value: List<PlantSpecies>): String {
        val type = object : TypeToken<List<PlantSpecies>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPlant(value: String): PlantSpecies {
        val type = PlantSpecies::class.java
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toPlantList(value: String): List<PlantSpecies> {
        val type = object : TypeToken<List<PlantSpecies>>() {}.type
        return gson.fromJson(value, type)
    }
}