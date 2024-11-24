package com.example.doodlejump.movement

import com.example.doodlejump.data.Platform
import com.example.doodlejump.data.PlatformType
import com.example.doodlejump.data.platformWidth

fun movePlatforms(
    platforms: MutableList<Platform>,
    screenWidth: Float,
) {

    platforms.filter { it.platformType == PlatformType.MOVING }.forEach { platform ->
        platform.x += platform.movingSpeed * platform.movingDirection
        if (platform.x <= 0 || platform.x + platformWidth >= screenWidth) {
            platform.movingDirection *= -1
        }
    }
}