package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

enum class BonusType {
    NONE,
    SPRING
}

enum class PlatfromType {
    STANDING,
    MOVING
}

data class Platform(

    var x: Float,
    var y: Float,
    var platformType: PlatfromType = PlatfromType.STANDING,
    var movingSpeed: Float = 0f,
    var bonusType: BonusType = BonusType.NONE,
    var movingDirection: Int = 1,

    )

@Composable
fun Platforms(
    platforms: MutableList<Platform>,
    screenWidth: Float,
    screenHeight: Float,
    modifier: Modifier
) {
    val greenPlatform = painterResource(R.drawable.greenbar)
    val bluePlatform = painterResource(R.drawable.bluebar)
    val springImage = painterResource(R.drawable.springtransformed)
    val screenHeightDP = LocalConfiguration.current.screenHeightDp.dp

    platforms.forEach { platform ->
        Box(
            modifier = Modifier
                .size(platformWidth.dp, platformHeight.dp)
                .offset(x = platform.x.dp, y = (screenHeightDP - platform.y.dp))
        ) {
            val platformImage =
                if (platform.platformType == PlatfromType.STANDING) greenPlatform else bluePlatform
            Image(
                painter = platformImage,
                contentDescription = "Platform",
                modifier = Modifier.size(platformWidth.dp, platformHeight.dp)
            )
            if (platform.bonusType == BonusType.SPRING) {
                Image(
                    painter = springImage,
                    contentDescription = "Spring",
                    modifier = Modifier
                        .size(15.dp)
                        .offset(x = (platformWidth / 4).dp, y = (-10).dp)
                )
            }
        }
    }
}


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
    platforms.filter { it.platformType == PlatfromType.MOVING }.forEach { platform ->
        platform.x += platform.movingSpeed * platform.movingDirection
        if (platform.x <= 0 || platform.x + platformWidth >= screenWidth) {
            platform.movingDirection *= -1
        }
    }
    // Если платформ на экране меньше нужного числа, добавляем новые
    if (platforms.isNotEmpty() && platforms.maxOf { it.y } < screenHeight) {
        while (platforms.size < maxPlatformsOnScreen) {

            val highestPlatformY = platforms.maxOf { it.y }

            // Генерируем новую платформу выше самой верхней
            val minPlatformGap = 30f
            val maxPlatformGap = 150f

            val platformGap =
                Random.nextFloat() * (maxPlatformGap - minPlatformGap) + minPlatformGap
            val newPlatformY = highestPlatformY + platformGap
            val newPlatformX = Random.nextFloat() * (screenWidth - platformWidth)

            val isMoving = Random.nextFloat() < 0.1f
            val platformType = if (isMoving) PlatfromType.MOVING else PlatfromType.STANDING
            val movingSpeed =
                if (platformType == PlatfromType.MOVING) Random.nextFloat() * 2 + 1 else 0f
            val bonus = if (Random.nextFloat() < 0.1f) BonusType.SPRING else BonusType.NONE

            // Добавляем новую платформу
            platforms.add(Platform(newPlatformX, newPlatformY, platformType, movingSpeed, bonus))
        }
    }
}