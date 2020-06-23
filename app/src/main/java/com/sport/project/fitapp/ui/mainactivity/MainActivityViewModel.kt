package com.sport.project.fitapp.ui.mainactivity

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sport.project.fitapp.R
import com.sport.project.fitapp.ui.stepcounter.data.DateChangeBroadcastReceiver
import com.sport.project.fitapp.ui.stepcounter.service.StepCounterService
import com.sport.project.fitapp.ui.stepcounter.utils.ObservePreferences

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences: ObservePreferences
    private var dateChangedBroadcastReceiver = DateChangeBroadcastReceiver()

    private val autoStepCounter = Observer { isEnabled: Boolean ->
        when(isEnabled) {
            true -> application.startService(Intent(application, StepCounterService::class.java))
            false -> application.stopService(Intent(application, StepCounterService::class.java))
        }
    }

    init {
        createNotificationChannel(application)
        preferences = ObservePreferences(application)
        preferences.stepCounting.observeForever(autoStepCounter)
        application.registerReceiver(dateChangedBroadcastReceiver, IntentFilter(Intent.ACTION_DATE_CHANGED))
    }

    private fun createNotificationChannel(application: Application) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                application.getString(R.string.channel_id),
                application.getString(R.string.channel_name),
                importance
            )
        } else {
            return
        }
        val notificationManager: NotificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onCleared() {
        getApplication<Application>().unregisterReceiver(dateChangedBroadcastReceiver)
        preferences.stepCounting.removeObserver(autoStepCounter)
        preferences.destroy()
    }
}

class MainActivityViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}