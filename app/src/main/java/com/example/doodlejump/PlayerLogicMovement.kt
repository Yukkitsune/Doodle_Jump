package com.example.doodlejump

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.delay

const val GRAVITY = -0.8f // Гравитация
const val JUMP_FORCE = 20f // Сила прыжка


@Composable
fun UpdatePlayerPosition(playerY: MutableState<Float>, velocity: MutableState<Float>,modifier: Modifier){
    //val platforms = remember { mutableStateListOf(*initialPlatforms.toTypedArray()) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    LaunchedEffect(Unit){
        while(true){
            delay(16L) // Частота обновления 60 FPS
            playerY.value += velocity.value
            velocity.value += GRAVITY
            /*if (playerY.value < 0f){
                playerY.value = 0f
                velocity.value = 0f
            }
            if (playerY.value > screenHeight) {
                playerY.value = screenHeight
                velocity.value = 0f
            }*/
        }
    }
}
@Composable
fun playerSetupY(platforms: List<Platform>):MutableState<Float>{
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val bottomPlatformY = initialPlatforms.minByOrNull { it.y}?.y?:0f
    val playerY = remember { mutableStateOf(screenHeight-bottomPlatformY-+20f) }
    return playerY
}
@Composable
fun playerSetupX(platforms: List<Platform>):MutableState<Float>{
    val bottomPlatformX = initialPlatforms.minByOrNull { it.x }?.x?:0f
    val playerX = remember{ mutableStateOf((bottomPlatformX+100f))}
    return playerX
}
@Composable
fun playerVelocity(): MutableState<Float>{
    val velocity = remember{mutableStateOf(0f)}
    return velocity
}
fun checkCollision(playerX:Float,playerY:Float, velocity:Float,platforms:List<Platform>):Boolean{
    if (velocity < 0f){
        return platforms.any{platform ->
            val platformTop = platform.y
            val platformBottom = platform.y+20f
            val platformLeft = platform.x
            val platformRight = platform.x + 100f
            playerY in platformTop..platformBottom && playerX in platformLeft..platformRight
        }
    }
    return false
}