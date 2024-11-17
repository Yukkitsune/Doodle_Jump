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
import com.example.doodlejump.ui.theme.doodleFontFamily

@Composable
fun DoodleJumpGameScreen(
    currentGame: MutableState<DoodleJumpGameState> = remember { mutableStateOf(DoodleJumpGameState()) },
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
        UpdateGame(score = score, currentGame = currentGame, platforms = platforms)

    }
}


@Composable
fun resetGame(
    viewModel: GameViewModel,
    currentGame: MutableState<DoodleJumpGameState>,
    score: MutableIntState,
    platforms: MutableList<Platform>,
    playerX: MutableState<Float>, // Позиция игрока по X
    playerY: MutableState<Float>, // Позиция игрока по Y
    screenHeight: Float,
    screenWidth: Float
) {
    // Сброс состояния игры
    currentGame.value =
        currentGame.value.copy(gameState = GameStatus.IDLE)  // Возвращаем игру в состояние "ожидания"
    platformShift = 0f
    platforms.clear()
    platforms.addAll(
        listOf(
            Platform(-80f, 0f),
            Platform(0f, 10f),
            Platform(80f, 20f),
            Platform(160f, 0f),
            Platform(160f, 180f),
            Platform(240f, 15f),
            Platform(320f, 20f),
            Platform(265f, 240f),
            Platform(105f, 380f),
            Platform(0f, 340f),
            Platform(400f, 0f)
        )
    )

    // Сброс счета
    score.intValue = 0
    playerX.value =
        viewModel.initialPlayerXPosition()//viewModel.playerXPosition  // Начальное значение для позиции X игрока (зависит от начального положения в вашей игре)
    playerY.value = viewModel.calculatePlayerStartY(
        screenHeight = screenHeight,
        platforms = platforms
    )  // Начальное значение для позиции Y игрока
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
fun MenuGameScreen(
    currentGame: MutableState<DoodleJumpGameState>,
    score: MutableIntState,
    platforms: MutableList<Platform>
) {
    val playerX = remember { mutableStateOf(0f) }
    val playerY = remember { mutableStateOf(0f) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
    val backgroundImage = painterResource(R.drawable.defaultmenu)
    val playButtonImage = painterResource(R.drawable.play)
    resetGame(
        viewModel = viewModel(),
        currentGame = currentGame,
        score = score,
        platforms = platforms,
        playerX = playerX,
        playerY = playerY,
        screenHeight = screenHeight,
        screenWidth = screenWidth
    )
    Image(
        painter = backgroundImage,
        contentDescription = "Menu Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Image(
            painter = painterResource(R.drawable.doodle_jump),
            contentDescription = "Doodle Jump",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
                .size(250.dp)

        )
        Button(
            onClick = {
                score.intValue = 0
                currentGame.value = currentGame.value.copy(gameState = GameStatus.STARTED)

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),

            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {

            Image(
                painter = playButtonImage,
                contentDescription = "Play Button",
                contentScale = ContentScale.Crop,
                modifier = Modifier


            )
        }
    }
}

@Composable
fun GameOverScreen(
    currentGame: MutableState<DoodleJumpGameState>,
    score: MutableIntState,
    bestScore: MutableIntState
) {
    val menuButton = painterResource(R.drawable.menu)
    val backgroundImage = painterResource(R.drawable.defaultmenu)

    if (score.intValue > bestScore.intValue) {
        bestScore.intValue = score.intValue
    }
    Image(
        painter = backgroundImage,
        contentDescription = "Menu Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Game Over!\nYour score: ${score.intValue}\nBest score: ${bestScore.intValue}",
            fontFamily = doodleFontFamily,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 120.dp)
        )
        Button(
            onClick = {
                score.intValue = 0
                currentGame.value =
                    currentGame.value.copy(gameState = GameStatus.IDLE) // Возврат в меню
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Image(
                painter = menuButton,
                contentDescription = "Menu Button",
                contentScale = ContentScale.Fit,
                modifier = Modifier


            )
        }
    }
}

@Composable
fun UpdateGame(
    viewModel: GameViewModel = viewModel(),
    currentGame: MutableState<DoodleJumpGameState>,
    score: MutableIntState,
    platforms: MutableList<Platform>
) {

    if (currentGame.value.gameState != GameStatus.STARTED) return
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    val playerX = viewModel.playerXPosition
    val playerY = playerSetupY(platforms)
    val velocityX = playerVelocityX()
    val velocityY = playerVelocityY()
    val mediaPlayer = setupMediaPlayer()

    PlayerDisplay(playerX = playerX, playerY = playerY)
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
        mediaPlayer = mediaPlayer,
        modifier = Modifier
    )
    ScoreDisplay(score = score, platformShift = platformShift)
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


