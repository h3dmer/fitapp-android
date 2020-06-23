package com.sport.project.fitapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sport.project.fitapp.db.entities.WorkoutExercise

class WorkoutConverter {

    @TypeConverter
    fun fromListObjects(list: List<WorkoutExercise>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<WorkoutExercise> {
        val listType = object : TypeToken<List<WorkoutExercise>>() {}.type
        return Gson().fromJson(value, listType)
    }
}