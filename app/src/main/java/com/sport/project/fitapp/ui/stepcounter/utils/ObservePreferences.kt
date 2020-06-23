package com.sport.project.fitapp.ui.stepcounter.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

class ObservePreferences(context: Context) {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private val _stepCounting = MutableLiveData(preferences.getBoolean("auto_step_counting", true))
    val stepCounting: LiveData<Boolean> get() = _stepCounting

    private val _notifications = MutableLiveData(preferences.getBoolean("notifications", false))
    val notifications: LiveData<Boolean> get() = _notifications

    private val prefListener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sp, key ->
            when (key) {
                "auto_step_counting" -> {
                    _stepCounting.value = sp.getBoolean(key, true)
                }
                "notifications" -> {
                    _notifications.value = sp.getBoolean(key, false)
                }
            }
        }

    init {
        preferences.registerOnSharedPreferenceChangeListener(prefListener)
    }

    fun setAutoStepCounting(status: Boolean) {
        preferences.edit().putBoolean("auto_step_counting", status).apply()
    }

    fun destroy() {
        preferences.unregisterOnSharedPreferenceChangeListener(prefListener)
    }
}