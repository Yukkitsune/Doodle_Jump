package com.example.doodlejump

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class GameViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val sensorManager: SensorManager =
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val _playerXPosition =
        mutableFloatStateOf(initialPlatforms.minByOrNull { it.y }?.x ?: 0f)

    fun initialPlayerXPosition(): Float {
        return initialPlatforms.minByOrNull { it.y }?.x ?: 0f
    }

    fun calculatePlayerStartY(screenHeight: Float, platforms: MutableList<Platform>): Float {
        val bottomPlatformY = initialPlatforms.minByOrNull { it.y }?.y ?: 0f
        return screenHeight - (bottomPlatformY + 60f)
    }

    val playerXPosition: MutableState<Float> = _playerXPosition
    val playerDirection = mutableStateOf(Direction.RIGHT)

    init {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    private fun updatePlayerPosition(tilt: Float) {
        // Здесь можно изменить скорость движения персонажа в зависимости от наклона
        val sensitivity = -3f // Настраиваемая чувствительность
        _playerXPosition.value += tilt * sensitivity
    }

    fun moveRight() {
        _playerXPosition.value += 20f
    }

    fun moveLeft() {
        _playerXPosition.value -= 20f
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Обрабатываем данные акселерометра
        val xAxisValue = event?.values?.get(0) // Получаем наклон по оси X
        if (xAxisValue != null) {
            updatePlayerPosition(xAxisValue)
            if (xAxisValue < -1) {
                playerDirection.value = Direction.RIGHT
            } else if (xAxisValue > 1) {
                playerDirection.value = Direction.LEFT
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

}
