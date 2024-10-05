package com.example.doodlejump

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

const val GRAVITY = -0.8f // Гравитация
const val JUMP_FORCE = 20f // Сила прыжка


@Composable
fun UpdatePlayerPosition(playerY: MutableState<Float>, velocity: MutableState<Float>,modifier: Modifier){
    LaunchedEffect(Unit){
        while(true){
            delay(16L) // Частота обновления 60 FPS
            playerY.value += velocity.value // Изменяем положение по Y
            if (velocity.value > GRAVITY) {
                velocity.value += GRAVITY * 0.5f// Применяем гравитацию
            }
            if (playerY.value < 0f){
                playerY.value = 0f
                velocity.value = 0f
            }
        }
    }
}


fun checkCollision(playerY: Float, velocity: Float, platforms: List<Platform>):Boolean{
    if (velocity < 0f) {
        return platforms
            .filterNot{it.x == 0f && it.y == 0f}
            .any { platform ->
                playerY in platform.y..(platform.y + 20f)
        }
    }
    return false
}