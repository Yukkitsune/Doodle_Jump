package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doodlejump.ui.theme.DoodleJumpTheme
import com.example.doodlejump.ui.theme.doodleFontFamily
import kotlin.math.abs

@Composable
fun DoodleJumpGameScreen(
    currentGame:MutableState<DoodleJumpGameState> = remember { mutableStateOf(DoodleJumpGameState()) },
    modifier: Modifier = Modifier
) {
    when (currentGame.value.gameState) {
        GameStatus.STARTED -> {
            StartGameScreen(currentGame)
        }
        GameStatus.IDLE -> {
            MenuGameScreen(currentGame)
        }
        GameStatus.PAUSED -> {
            PauseScreen(currentGame)
        }
        GameStatus.GAMEOVER -> {
            GameOverScreen(currentGame)
        }
    }

}

@Composable
fun StartGameScreen(currentGame: MutableState<DoodleJumpGameState>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundDisplay()
        PauseDisplay(currentGame)
        UpdateGame(currentGame = currentGame)

    }
}
@Composable
fun PauseScreen(currentGame: MutableState<DoodleJumpGameState>) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Кнопка "Продолжить"
        Button(
            onClick = {
                currentGame.value = currentGame.value.copy(gameState = GameStatus.STARTED) // Возвращаемся к игре
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("RESUME GAME")
        }
    }
}
@Composable
fun MenuGameScreen(currentGame: MutableState<DoodleJumpGameState>){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Button(
            onClick = {
                currentGame.value = currentGame.value.copy(gameState = GameStatus.STARTED) // Перемещаем игрока вправо на 20 единиц
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Text("START GAME, gameState = ${currentGame.value.gameState}")
        }
    }
}
@Composable
fun GameOverScreen(currentGame: MutableState<DoodleJumpGameState>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Game Over! Your score: ${ScoreDisplay(platformShift = platformShift)}",
            modifier = Modifier.align(Alignment.Center)
        )
        Button(
            onClick = {
                currentGame.value = currentGame.value.copy(gameState = GameStatus.IDLE) // Возврат в меню
            },
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
            Text("RETURN TO MENU")
        }
    }
}
@Composable
fun UpdateGame( viewModel: GameViewModel = viewModel(), currentGame: MutableState<DoodleJumpGameState>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    val platforms = remember { mutableStateOf(initialPlatforms) }
    val playerX = viewModel.playerXPosition
    val playerY = playerSetupY(platforms.value)
    val velocityX = playerVelocityX()
    val velocityY = playerVelocityY()

    PlayerDisplay(playerX=playerX, playerY = playerY)
    UpdatePlayerPosition(
        playerX = playerX,
        playerY = playerY,
        velocityX = velocityX,
        velocityY = velocityY,
        platforms = platforms.value,
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        onPlatformShift = { shift -> platformShift += shift },
        currentGame = currentGame,
        modifier = Modifier
    )
    ScoreDisplay(platformShift = platformShift)
    updatePlatforms(
        playerY = playerY.value,
        platforms = platforms.value,
        screenWidth = screenWidth,
        screenHeight = screenHeight
    )
    Platforms(
        platforms = platforms.value, screenWidth = screenWidth, screenHeight = screenHeight,
        modifier = Modifier.fillMaxSize()
    )


}


@Composable
fun BackgroundDisplay() {
    val backgroundImage = painterResource(R.drawable.background_1x)
    val scoreImage = painterResource(R.drawable.score_1x)
    Image(
        painter = backgroundImage,
        contentDescription = "Game Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()

    )
    Image(
        painter = scoreImage,
        contentDescription = "Score Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun PauseDisplay(currentGame: MutableState<DoodleJumpGameState>) {
    val pauseImage = painterResource(R.drawable.pause_screen)
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                currentGame.value = currentGame.value.copy(gameState = GameStatus.PAUSED)
            },
            //enabled = ,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(bottom = 30.dp)
        ) {
            Image(
                painter = pauseImage,
                contentDescription = "Pause Image",
                modifier = Modifier
                    .size(30.dp)
                    .padding(start = 10.dp)

            )
        }
    }
}