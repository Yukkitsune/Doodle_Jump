package com.example.doodlejump.logic

import com.example.doodlejump.data.BonusType
import com.example.doodlejump.data.Platform
import com.example.doodlejump.data.PlatformType
import com.example.doodlejump.data.platformWidth
import com.example.doodlejump.data.platformsMutex
import com.example.doodlejump.movement.movePlatforms
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

fun updatePlatformsAsync(
    platforms: MutableList<Platform>,
    screenWidth: Float,
    screenHeight: Float
) = CoroutineScope(Dispatchers.Default).launch {
    platformsMutex.withLock {
        platforms.removeIf { it.y < -30f }
    }

    movePlatforms(
        platforms = platforms,
        screenWidth = screenWidth
    )
    if (platforms.isEmpty() || platforms.maxOf { it.y } < screenHeight) {
        generatePlatforms(platforms, screenWidth)
    }
}

fun generatePlatforms(
    platforms: MutableList<Platform>,
    screenWidth: Float,
) {
    val maxPlatformsOnScreen = 12
    while (platforms.size < maxPlatformsOnScreen) {
        val highestPlatformY = platforms.maxOfOrNull { it.y } ?: 0f
        val platformGap = Random.nextFloat() * 120 + 30
        val newPlatformY = highestPlatformY + platformGap
        val newPlatformX = Random.nextFloat() * (screenWidth - platformWidth)
        val isMoving = Random.nextFloat() < 0.1f
        val platformType = if (isMoving) PlatformType.MOVING else PlatformType.STANDING
        val movingSpeed =
            if (platformType == PlatformType.MOVING) Random.nextFloat() * 2 + 1 else 0f
        val bonus =
            when (Random.nextFloat()) {
                in 0f..0.1f -> {
                    BonusType.SPRING
                }

                in 0f..0.05f -> {
                    BonusType.HELICOPTER
                }

                else -> {
                    BonusType.NONE
                }
            }

        platforms.add(Platform(newPlatformX, newPlatformY, platformType, movingSpeed, bonus))
    }
}