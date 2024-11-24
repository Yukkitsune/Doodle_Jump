package com.example.doodlejump.logic

import android.content.Context
import android.media.MediaPlayer
import com.example.doodlejump.data.HELICOPTER_FLY_FORCE
import com.example.doodlejump.data.JUMP_FORCE
import com.example.doodlejump.data.SPRING_JUMP_FORCE
import com.example.doodlejump.data.BonusType
import com.example.doodlejump.data.Platform
import com.example.doodlejump.data.platformHeight
import com.example.doodlejump.data.platformWidth
import com.example.doodlejump.data.playerHeight
import com.example.doodlejump.data.playerWidth
import com.example.doodlejump.ui.components.playSoundAsync

fun checkPlatformCollision(
    playerX: Float,
    playerY: Float,
    velocityY: Float,
    platforms: MutableList<Platform>,
    screenHeight: Float,
    mediaPlayer: Map<String, MediaPlayer>,
    context: Context
): Pair<Boolean, Float> {
    val sharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
    val isSoundEnabled = sharedPreferences.getBoolean("sound_enabled", true)
    if (velocityY > 0f) {
        for (platform in platforms) {
            val platformTop = screenHeight - platform.y
            val platformBottom = screenHeight - (platform.y + platformHeight)
            val platformLeft = platform.x
            val platformRight = platform.x + platformWidth - 30f
            val isWithinY =
                playerY + playerHeight >= platformTop && playerY <= platformBottom
            val isWithinX = playerX + playerWidth / 2 >= platformLeft && playerX <= platformRight
            if (isWithinX && isWithinY) {
                when (platform.bonusType) {
                    BonusType.SPRING -> {
                        val springLeft = platform.x + (platformWidth / 3)
                        val springRight = platform.x + (2 * platformWidth / 3)
                        val isOnSpring =
                            playerX + playerWidth in springLeft..springRight
                        if (isOnSpring) {
                            if (isSoundEnabled) playSoundAsync(mediaPlayer["spring"]!!)
                            return true to SPRING_JUMP_FORCE
                        } else {
                            if (isSoundEnabled) playSoundAsync(mediaPlayer["jump"]!!)
                            return true to JUMP_FORCE
                        }
                    }

                    BonusType.HELICOPTER -> {
                        val helicopterLeft = platform.x + (platformWidth / 3)
                        val helicopterRight = platform.x + (2 * platformWidth / 3)
                        val isOnHelicopter =
                            playerX + playerWidth in helicopterLeft..helicopterRight
                        if (isOnHelicopter) {
                            return true to HELICOPTER_FLY_FORCE
                        } else {
                            if (isSoundEnabled) playSoundAsync(mediaPlayer["jump"]!!)
                            return true to JUMP_FORCE
                        }
                    }

                    else -> {
                        if (isSoundEnabled) playSoundAsync(mediaPlayer["jump"]!!)
                        return true to JUMP_FORCE
                    }
                }
            }
        }
    }
    return false to 0f
}