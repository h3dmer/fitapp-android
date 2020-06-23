package com.sport.project.fitapp.ui.calculatecalories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CaloriesViewModel: ViewModel() {

    private val activityText = MutableLiveData<String>()
    private val dailyActivity = MutableLiveData<Double>()

    val activityTextLiveData = activityText
    val dailyActivityLiveData = dailyActivity


    init {
        setDailyActivity(0.0)
    }

    fun setActivityText(text: String) {
        activityText.value = text
    }

    fun setDailyActivity(value: Double) {
        dailyActivity.value = value
    }

    fun calculateDailyCalories(height: Int, weight: Int, age: Int, gender: Int, physicalActivity: Double): Int {
        return if (gender == 0) {
            ((66.47 + (13.7 * weight) + (5.0 * height) - (6.76 * age)) * physicalActivity).toInt()
        } else {
            ((66.47 + (13.7 * weight) + (5.0 * height) - (6.76 * age)) * physicalActivity).toInt()
        }
    }
}