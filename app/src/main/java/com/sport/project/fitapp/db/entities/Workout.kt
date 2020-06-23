package com.sport.project.fitapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sport.project.fitapp.db.converters.WorkoutConverter
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var date: String = "",
    @TypeConverters(WorkoutConverter::class)
    var workoutExercises: List<WorkoutExercise> = listOf()
) : Parcelable

@Parcelize
data class WorkoutExercise(
//    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var muscle: String = "",
    var sets: Int = 0,
    var reps: Int = 0,
    var isFinished: Boolean = false
) : Parcelable
