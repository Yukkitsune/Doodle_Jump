package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random


data class Platform(

    val x: Float,
    var y: Float
)

@Composable
fun Platforms(platforms: MutableList<Platform>, screenWidth: Float, screenHeight: Float, modifier: Modifier) {
    val platformImage = painterResource(R.drawable.greenbar)
    val screenHeightDP = LocalConfiguration.current.screenHeightDp.dp

    platforms.forEach { platform ->
        Box(
            modifier = Modifier
                .size(platformWidth.dp, platformHeight.dp)
                .offset(x = platform.x.dp, y = (screenHeightDP - platform.y.dp))
        ) {
            Image(
                painter = platformImage,
                contentDescription = "Platform",
                modifier = Modifier.size(platformWidth.dp, platformHeight.dp)
            )
        }
    }
}

/*
fun generateRandomPlatforms(
    screenWidth: Float,
    screenHeight: Float,
    count: Int
): MutableList<Platform> {
    val platforms = mutableListOf<Platform>()

    // Начальная позиция по Y (внизу экрана)
    var currentY = screenHeight - 100f
    val minPlatformGap = 150f
    val maxPlatformGap = 250f // Задаем случайный промежуток между платформами

    for (i in 0 until count) {
        // Генерация случайной позиции X в пределах ширины экрана
        val x = Random.nextFloat() * (screenWidth - platformWidth)
        platforms.add(Platform(x, currentY))

        // Случайный промежуток между платформами
        val platformGap = Random.nextFloat() * (maxPlatformGap - minPlatformGap) + minPlatformGap
        currentY -= platformGap // Поднимаем следующую платформу выше
    }

    return platforms
}
*/

fun updatePlatforms(
    playerY: Float,
    platforms: MutableList<Platform>,
    screenWidth: Float,
    screenHeight: Float
) {
    if (platforms.isEmpty()) return
    val maxPlatformsOnScreen = 20

    // Удаляем платформы, которые вышли за нижнюю границу экрана

    platforms.removeIf { it.y < -50f }
    // Если платформ на экране меньше нужного числа, добавляем новые
    if (platforms.isNotEmpty() && platforms.maxOf { it.y } < screenHeight){
        while (platforms.size < maxPlatformsOnScreen) {

            val highestPlatformY = platforms.maxOf { it.y }

            // Генерируем новую платформу выше самой верхней
            val minPlatformGap = 30f
            val maxPlatformGap = 150f
            val platformGap = Random.nextFloat() * (maxPlatformGap - minPlatformGap) + minPlatformGap
            val newPlatformY = highestPlatformY + platformGap
            val newPlatformX = Random.nextFloat() * (screenWidth - platformWidth)

            // Добавляем новую платформу
            platforms.add(Platform(newPlatformX, newPlatformY))
            /*println("New platform added ${Platform(newPlatformX,newPlatformY)}")*/
        }
    }
}