package com.sport.project.fitapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "daily_steps_table")
data class DailySteps(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var date: String = "",
    var steps: Int = 0,
    var goal: Int = 0
) {
    fun progressPercentage(): Int = ((steps.toDouble() / goal) * 100).toInt()

    fun stepsToMeters(): Double = (steps * 0.762) / 1000

    fun basicStepCaloriesBurned(): Int = (steps.toDouble() * 0.04).toInt()

    fun formatDisplayingDate(): String {
        val parser =  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(parser.parse(date)!!)
    }
}