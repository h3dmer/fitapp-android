package com.sport.project.fitapp.ui.stepcounter.ui

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sport.project.fitapp.ui.stepcounter.data.StepDetector
import com.sport.project.fitapp.ui.stepcounter.data.StepListener

class StaticStepCounterViewModel(application: Application) : AndroidViewModel(application),
    SensorEventListener, StepListener {

    private lateinit var sensorManager: SensorManager
    private var stepDetector = StepDetector()

    private val _sensorData = MutableLiveData<String>()
    val sensorData: LiveData<String> get() = _sensorData

    private val _numSteps = MutableLiveData<Int>()
    val numSteps: LiveData<Int> get() = _numSteps

    private var stepCounterOffset: Boolean = true
    private var offset: Int = 0


    init {
        _numSteps.value = 0
    }

    fun registerSensor() {
        sensorManager =
            getApplication<Application>().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepDetector.registerListener(this)
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    fun unregisterSensor() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (stepCounterOffset) {
            offset = event!!.values[0].toInt()
            stepCounterOffset = false
        }
        _sensorData.value = "" + (event!!.values[0].plus(-offset))
    }

    override fun step(timeNs: Long) {
        //_numSteps.value = numSteps.value?.plus(1)
    }

}