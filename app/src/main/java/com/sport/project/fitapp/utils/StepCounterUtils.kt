package com.sport.project.fitapp.utils

import java.util.*

object StepCounterUtils {

    val calendar: Calendar
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar
        }


    fun stepsToMeters(steps: Number): Double {
        return steps.toInt() * 0.762 / 1000
    }
}