package com.example.doodlejump.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doodlejump.data.Platform
import com.example.doodlejump.logic.checkPlatformCollision
import com.example.doodlejump.models.GameViewModel
import kotlin.math.abs

/*@Composable
fun testIndications(
    playerX: MutableState<Float>,
    playerY: MutableState<Float>,
    velocityX: MutableState<Float>,
    velocityY: MutableState<Float>,
    platforms: MutableList<Platform>,
    screenWidth: Float,
    screenHeight: Float
) {
    Text(
        text = "playerX = ${playerX.value}\n playerY = ${playerY.value}\n velocityX = ${velocityX.value}\n velocityY = ${velocityY.value}\n" +
                "collision = ${
                    checkPlatformCollision(
                        playerX = playerX.value,
                        playerY = playerY.value,
                        velocityY = 0f,
                        platforms = platforms,
                        screenHeight = screenHeight,
                        mediaPlayer = setupMediaPlayer()
                    )
                }\n" +
                "playerYScreenHeight = ${abs(playerY.value - screenHeight)}\n" +
                "platforms = ${platforms}\n"+
        "platform[0] = ${platforms[0].y + screenHeight}\n" +
        "platform[1] = ${platforms[1].y + screenHeight}\n" +
        "platform[2] = ${platforms[2].y + screenHeight}\n" +
        "platform[3] = ${platforms[3].y + screenHeight}\n"
    )
}*/
@Composable
fun MoveRight(playerX: Float, viewModel: GameViewModel = viewModel()) {
    Button(
        onClick = {
            viewModel.moveRight() // Перемещаем игрока вправо на 20 единиц
        },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text("Move Right")
    }
}

@Composable
fun MoveLeft(playerX: Float, viewModel: GameViewModel = viewModel()) {
    Button(
        onClick = {
            viewModel.moveLeft() // Перемещаем игрока вправо на 20 единиц
        },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text("Move Left")
    }
}
/*testIndications(
        playerX = playerX.value,
        playerY = playerY,
        velocityX = velocityX,
        velocityY = velocityY,
        platforms = platforms.value,
        screenWidth = screenWidth,
        screenHeight = screenHeight
    )*/