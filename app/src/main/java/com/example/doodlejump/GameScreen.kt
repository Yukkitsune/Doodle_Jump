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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DoodleJumpGameScreen(
    currentGame:MutableState<DoodleJumpGameState> = remember { mutableStateOf(DoodleJumpGameState()) },
    modifier: Modifier = Modifier
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    val score = remember { mutableIntStateOf(0) }
    val bestScore = remember { mutableIntStateOf(0) }
    val platforms = remember { initialPlatforms.toMutableList() }
    when (currentGame.value.gameState) {
        GameStatus.STARTED -> {
            StartGameScreen(currentGame = currentGame, score = score, platforms = platforms)
        }
        GameStatus.IDLE -> {
            MenuGameScreen(currentGame = currentGame, score = score, platforms = platforms)
        }
        GameStatus.PAUSED -> {
            PauseScreen(currentGame = currentGame)
        }
        GameStatus.GAMEOVER -> {
            GameOverScreen(currentGame = currentGame, score = score, bestScore = bestScore)
        }
    }

}

@Composable
fun StartGameScreen(
    currentGame: MutableState<DoodleJumpGameState>,
    score: MutableIntState,
    platforms: MutableList<Platform>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundDisplay()
        PauseDisplay(currentGame)
        UpdateGame(score = score, currentGame = currentGame, platforms = platforms)

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
fun MenuGameScreen(
    currentGame: MutableState<DoodleJumpGameState>,
    score: MutableIntState,
    platforms: MutableList<Platform>
){
    val playerX = remember { mutableStateOf(0f) }
    val playerY = remember { mutableStateOf(0f) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    resetGame(
        viewModel = viewModel(),
        currentGame = currentGame,
        score = score,
        platforms = platforms,
        playerX = playerX,
        playerY = playerY,
        screenHeight = screenHeight
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Button(
            onClick = {
                score.intValue = 0
                currentGame.value = currentGame.value.copy(gameState = GameStatus.STARTED)

            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Text("START GAME")
        }
    }
}
fun resetGame(
    viewModel: GameViewModel,
    currentGame: MutableState<DoodleJumpGameState>,
    score: MutableIntState,
    platforms: MutableList<Platform>,
    playerX: MutableState<Float>, // Позиция игрока по X
    playerY: MutableState<Float>, // Позиция игрока по Y
    screenHeight: Float
) {
    // Сброс состояния игры
    currentGame.value =
        currentGame.value.copy(gameState = GameStatus.IDLE)  // Возвращаем игру в состояние "ожидания"
    platformShift = 0f
    platforms.clear()
    for (i in 0..initialPlatforms.size-1){
        platforms += initialPlatforms[i]
    }
    for (i in 0..initialPlatforms.size-1){
        println(platforms[i])
    }
    // Сброс счета
    score.intValue = 0
    playerX.value = viewModel.initialPlayerXPosition()//viewModel.playerXPosition  // Начальное значение для позиции X игрока (зависит от начального положения в вашей игре)
    playerY.value = viewModel.calculatePlayerStartY(screenHeight = screenHeight, platforms = platforms )  // Начальное значение для позиции Y игрока
    println("GAME RESET")
}
@Composable
fun GameOverScreen(currentGame: MutableState<DoodleJumpGameState>, score:MutableIntState, bestScore:MutableIntState) {
    if (score.intValue > bestScore.intValue){
        bestScore.intValue = score.intValue
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Game Over!\nYour score: ${score.intValue}\nBest score: ${bestScore.intValue}",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center).padding(16.dp)
        )
        Button(
            onClick = {
                score.intValue = 0
                currentGame.value = currentGame.value.copy(gameState = GameStatus.IDLE) // Возврат в меню
            },
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
            Text("RETURN TO MENU")
        }
    }
}
@Composable
fun UpdateGame(
    viewModel: GameViewModel = viewModel(),
    currentGame: MutableState<DoodleJumpGameState>,
    score: MutableIntState,
    platforms: MutableList<Platform>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    //var platforms = remember { mutableStateOf(initialPlatforms) }
    val playerX = viewModel.playerXPosition
    val playerY = playerSetupY(platforms)
    val velocityX = playerVelocityX()
    val velocityY = playerVelocityY()

    PlayerDisplay(playerX=playerX, playerY = playerY)
    UpdatePlayerPosition(
        playerX = playerX,
        playerY = playerY,
        velocityX = velocityX,
        velocityY = velocityY,
        platforms = platforms,
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        onPlatformShift = { shift -> platformShift += shift },
        currentGame = currentGame,
        modifier = Modifier
    )
    ScoreDisplay(score = score,platformShift = platformShift)
    updatePlatforms(
        playerY = playerY.value,
        platforms = platforms,
        screenWidth = screenWidth,
        screenHeight = screenHeight
    )
    Platforms(
        platforms = platforms,
        screenWidth = screenWidth,
        screenHeight = screenHeight,
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