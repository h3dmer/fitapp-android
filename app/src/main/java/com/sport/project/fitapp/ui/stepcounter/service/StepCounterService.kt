package com.sport.project.fitapp.ui.stepcounter.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import com.sport.project.fitapp.R
import com.sport.project.fitapp.db.AppDatabase
import com.sport.project.fitapp.db.dao.DailyStepsDao
import com.sport.project.fitapp.ui.mainactivity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class StepCounterService : Service(), SensorEventListener {

    private var steps = 0
    private lateinit var dailySteps: DailyStepsDao
    private lateinit var sensorManager: SensorManager
    private var disposables = CompositeDisposable()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

        val notification: Notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(this, getString(R.string.channel_id))
                    .setContentTitle(getString(R.string.auto_step_count))
                    .setContentText(getString(R.string.automatic_step_counter_text))
                    .setSmallIcon(R.drawable.fit_logo)
                    .build()
            } else {
                return
            }

        startForeground(R.integer.step_counter_notification_id, notification)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        dailySteps = AppDatabase.getInstance(this).dailyStepsDao()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        val step = event!!.values[0].toInt()
        if (steps == 0) {
            steps = step
        } else {
            val deltaSteps = step - steps
            steps = step
            dailySteps
                .updateDailySteps(deltaSteps)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(disposables)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        sensorManager.unregisterListener(this)
    }
}