package com.sport.project.fitapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sport.project.fitapp.db.converters.StringListConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "all_exercises")
data class Exercise(
    @PrimaryKey
    var id: Int? = null,
    var name: String = "",
    @TypeConverters(StringListConverter::class)
    var types: List<String> = listOf()
) : Parcelable

data class ListExercises(
    var items: List<Exercise>
)