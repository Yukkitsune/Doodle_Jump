package com.example.doodlejump.movement

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.doodlejump.data.GRAVITY
import com.example.doodlejump.data.DoodleJumpGameState
import com.example.doodlejump.data.GameStatus
import com.example.doodlejump.data.Platform
import com.example.doodlejump.data.initialPlatforms
import com.example.doodlejump.logic.checkPlatformCollision
import com.example.doodlejump.data.playerWidth
import com.example.doodlejump.models.GameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    viewModel: GameViewModel,
    currentScore: Int,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
    val isSoundEnabled = sharedPreferences.getBoolean("sound_enabled", true)
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.Default) {
            while (currentGame.value.gameState == GameStatus.STARTED) {
                delay(16L)
                playerX.value += velocityX.value
                velocityY.value += GRAVITY
                if (playerY.value < screenHeight / 2) {
                    val platformShift = velocityY.value
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
                    mediaPlayer = mediaPlayer,
                    context = context
                )
                if (collisionCheck) {
                    velocityY.value = jumpForce
                }
                if (playerY.value - 20f > screenHeight) {
                    if (isSoundEnabled) {
                        mediaPlayer["gameover"]?.apply {
                            if (!isPlaying) {
                                start()
                                setOnCompletionListener {
                                    scope.launch(Dispatchers.Main) {
                                        viewModel.setGameOver(score = currentScore)
                                    }
                                }
                            }
                        }
                    } else {
                        scope.launch(Dispatchers.Main) {
                            viewModel.setGameOver(score = currentScore)
                        }
                    }

                }
                if (playerX.value + playerWidth < 0f) {
                    playerX.value = screenWidth
                } else if (playerX.value > screenWidth) {
                    playerX.value = -playerWidth
                }
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