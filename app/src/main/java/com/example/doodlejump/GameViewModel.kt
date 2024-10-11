package com.example.doodlejump

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel

class GameViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val sensorManager: SensorManager =
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val _playerXPosition = mutableListOf(0f)
    override fun onSensorChanged(p0: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}
