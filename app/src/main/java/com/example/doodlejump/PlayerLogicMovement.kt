package com.example.doodlejump

import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.delay

const val GRAVITY = 0.8f // Гравитация
const val JUMP_FORCE = -20f // Сила прыжка


@Composable
fun UpdatePlayerPosition(
    playerX: MutableState<Float>,
    playerY: MutableState<Float>,
    velocityX: MutableState<Float>,
    velocityY: MutableState<Float>,
    platforms: MutableList<Platform>,
    screenWidth: Float,
    screenHeight: Float,
    modifier: Modifier
) {

    LaunchedEffect(Unit) {
        while (true) {
            delay(16L) // Частота обновления 60 FPS

            // Обновление координат по X и Y с учетом текущей скорости
            playerY.value += velocityY.value
            playerX.value += velocityX.value

            // Применение гравитации
            velocityY.value += GRAVITY

            // Проверка касания платформ
            if (checkCollisionJump(
                    playerX = playerX.value,
                    playerY = playerY.value,
                    velocityY = velocityY.value,
                    platforms = platforms,
                    screenHeight = screenHeight
                )
            ) {
                // Если игрок касается платформы, происходит прыжок
                velocityY.value = JUMP_FORCE
                println("JUMP")
            }
            if (checkCollisionBottom(
                    playerX = playerX.value,
                    playerY = playerY.value,
                    velocityY = velocityY.value,
                    platforms = platforms,
                    screenHeight = screenHeight
                )
            ) {
                velocityY.value = GRAVITY
                println("Fall")
            }
            // Ограничение движения по оси Y (не позволяем персонажу упасть за пределы экрана)
            if (playerY.value > screenHeight) {
                playerY.value = screenHeight - (bottomPlatformY + 60f)
                velocityY.value = 0f // Обнуляем скорость после достижения нижнего края экрана
            }

            // Дополнительная проверка на левый/правый край экрана (для оси X)
            // Можно добавить условие для отражения от краёв экрана
            if (playerX.value < 0f) {
                playerX.value = 0f
            } else if (playerX.value + 40f > screenWidth) {
                playerX.value = screenWidth - 50
            }
        }
    }
}

@Composable
fun playerSetupY(platforms: List<Platform>): MutableState<Float> {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val bottomPlatformY = initialPlatforms.minByOrNull { it.y }?.y ?: 0f
    val playerY = remember { mutableStateOf(screenHeight - (bottomPlatformY + 60f)) }
    return playerY
}

@Composable
fun playerSetupX(platforms: List<Platform>): MutableState<Float> {
    val bottomPlatformX = initialPlatforms.minByOrNull { it.y }?.x ?: 0f
    val playerX = remember { mutableStateOf((bottomPlatformX)) }
    return playerX
}

@Composable
fun playerVelocityY(): MutableState<Float> {
    val velocity = remember { mutableStateOf(0f) }
    return velocity
}

@Composable
fun playerVelocityX(): MutableState<Float> {
    val velocity = remember { mutableStateOf(0f) }
    return velocity
}

fun checkCollisionJump(
    playerX: Float,
    playerY: Float,
    velocityY: Float,
    platforms: MutableList<Platform>,
    screenHeight: Float
): Boolean {
    if (velocityY > 0f) {
        return platforms.any { platform ->
            val platformTop = screenHeight - platform.y
            val platformBottom = screenHeight - (platform.y + platformHeight)
            val platformLeft = platform.x
            val platformRight = platform.x + platformWidth - 30f
            val isWithinY =
                playerY + playerHeight / 2 + 10f >= platformTop && playerY <= platformBottom
            val isWithinX = playerX + playerWidth / 2 >= platformLeft && playerX <= platformRight
            isWithinX && isWithinY
        }
    }
    return false
}

// Проверка на столкновение головы персонажа с платформой сверху
fun checkCollisionBottom(
    playerX: Float,
    playerY: Float,
    velocityY: Float,
    platforms: MutableList<Platform>,
    screenHeight: Float
): Boolean {
    if (velocityY < 0f) {
        return platforms.any { platform ->
            val platformTop = screenHeight - platform.y
            val platformBottom = screenHeight - (platform.y + platformHeight)
            val platformLeft = platform.x
            val platformRight = platform.x + platformWidth - 30f
            val isWithinY = playerY in platformBottom..platformBottom + 20f
            val isWithinX = playerX + playerWidth / 2 >= platformLeft && playerX <= platformRight
            isWithinX && isWithinY
        }
    }
    return false
}