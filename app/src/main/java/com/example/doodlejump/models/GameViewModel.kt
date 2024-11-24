package com.example.doodlejump.models

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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.doodlejump.data.Direction
import com.example.doodlejump.data.DoodleJumpGameState
import com.example.doodlejump.data.GameStatus
import com.example.doodlejump.data.Platform
import com.example.doodlejump.data.initialPlatforms

class GameViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {
    private val _gameState = MutableLiveData(DoodleJumpGameState())
    val gameState: LiveData<DoodleJumpGameState> get() = _gameState
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
        val sensitivity = -3f
        _playerXPosition.value += tilt * sensitivity
    }

    fun moveRight() {
        _playerXPosition.value += 20f
    }

    fun moveLeft() {
        _playerXPosition.value -= 20f
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val xAxisValue = event?.values?.get(0)
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

    fun setGameOver(score: Int) {
        _gameState.value = _gameState.value?.copy(
            gameState = GameStatus.GAMEOVER,
            isGameOver = true,
            score = score
        )
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

}
