package com.example.doodlejump

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.delay


@Composable
fun UpdatePlayerPosition(
    playerX: MutableState<Float>,
    playerY: MutableState<Float>,
    velocityX: MutableState<Float>,
    velocityY: MutableState<Float>,
    platforms: MutableList<Platform>,
    screenWidth: Float,
    screenHeight: Float,
    onPlatformShift: (Float) -> Unit,
    currentGame: MutableState<DoodleJumpGameState>,
    mediaPlayer: Map<String, MediaPlayer>,
    modifier: Modifier
) {
    LaunchedEffect(Unit) {

        while (true) {
            delay(16L) // Частота обновления 60 FPS
            // Обновление координат по X и Y с учетом текущей скорости
            playerX.value += velocityX.value

            // Применение гравитации
            velocityY.value += GRAVITY
            if (playerY.value < screenHeight / 2) {
                val platformShift = velocityY.value
                // Если игрок поднимается выше зоны покоя, двигаем платформы вниз
                for (platform in platforms) {
                    platform.y += platformShift
                }
                onPlatformShift(platformShift)
                playerY.value += GRAVITY
            } else {
                playerY.value += velocityY.value
            }
            val (collisionCheck, jumpForce) = checkPlatformCollision(
                playerX = playerX.value,
                playerY = playerY.value,
                platforms = platforms,
                velocityY = velocityY.value,
                screenHeight = screenHeight,
                mediaPlayer = mediaPlayer
            )
            if (collisionCheck) {
                velocityY.value = jumpForce
            }
            // Ограничение движения по оси Y (не позволяем персонажу упасть за пределы экрана)
            if (playerY.value - 20f > screenHeight) {
                mediaPlayer["gameover"]?.apply {
                    if (!isPlaying) {
                        start()
                        setOnCompletionListener {
                            currentGame.value =
                                currentGame.value.copy(gameState = GameStatus.GAMEOVER)
                        }
                    }
                }
            }

            // Дополнительная проверка на левый/правый край экрана (для оси X)
            if (playerX.value + playerWidth < 0f) {
                playerX.value = screenWidth
            } else if (playerX.value > screenWidth) {
                playerX.value = -playerWidth
            }
        }
    }
}

@Composable
fun playerSetupY(platforms: MutableList<Platform>): MutableState<Float> {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val bottomPlatformY = initialPlatforms.minByOrNull { it.y }?.y ?: 0f
    val playerY = remember { mutableStateOf(screenHeight - (bottomPlatformY + 60f)) }
    return playerY
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

fun checkPlatformCollision(
    playerX: Float,
    playerY: Float,
    velocityY: Float,
    platforms: MutableList<Platform>,
    screenHeight: Float,
    mediaPlayer: Map<String, MediaPlayer>
): Pair<Boolean, Float> {
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
                            mediaPlayer["spring"]?.start()
                            return true to SPRING_JUMP_FORCE
                        } else {
                            mediaPlayer["jump"]?.start()
                            return true to JUMP_FORCE
                        }
                    }

                    else -> {
                        mediaPlayer["jump"]?.start()
                        return true to JUMP_FORCE
                    }
                }
            }
        }
    }
    return false to 0f
}

