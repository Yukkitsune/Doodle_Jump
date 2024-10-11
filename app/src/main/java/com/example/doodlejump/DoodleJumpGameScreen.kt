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
import com.example.doodlejump.ui.theme.DoodleJumpTheme
import com.example.doodlejump.ui.theme.doodleFontFamily

@Composable
fun DoodleJumpGameScreen(
) {
    InterfaceImage()
}

@Composable
fun InterfaceImage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        BackgroundDisplay()
        PauseGame()

        PlayerDisplay()
        //Platforms(initialPlatforms, modifier = Modifier.fillMaxSize())
        //Platforms(modifier = Modifier.fillMaxSize())

    }
}

@Composable
fun PlayerDisplay() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    val platforms = initialPlatforms
    //var platforms = generateRandomPlatforms(screenWidth,screenHeight,10)
    val playerX = playerSetupX(platforms)
    val playerY = playerSetupY(platforms)
    val velocityX = playerVelocityX()
    val velocityY = playerVelocityY()
    val platformGap = 100f
    val maxY =
        remember { mutableStateOf((screenHeight - (bottomPlatformY + platformHeight) - playerY.value)) }
    val score = remember { mutableStateOf(0) }
    Platforms(screenWidth = screenWidth, screenHeight = screenHeight, Modifier.fillMaxSize())
    testIndications(
        playerX = playerX,
        playerY = playerY,
        velocityX = velocityX,
        velocityY = velocityY,
        platforms = platforms,
        screenWidth = screenWidth,
        screenHeight = screenHeight
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PlayerCharacter(playerX = playerX.value, playerY = playerY.value, modifier = Modifier)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            MoveRight(playerX)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            MoveLeft(playerX)
        }
    }
    UpdatePlayerPosition(
        playerX = playerX,
        playerY = playerY,
        velocityX = velocityX,
        velocityY = velocityY,
        platforms = platforms,
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        Modifier
    )
    UpdateMaxY(playerY = playerY.value, maxY = maxY)
    score.value = CalculateScore(playerY.value, maxY, screenHeight)
    ScoreDisplay(score = score)
    //updatePlatforms(playerY.value, platforms,screenWidth, platformGap)

}

@Composable
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
        text = "playerX = ${playerX.value}\n playerY = ${playerY.value}\n velocityX = ${velocityX.value}\n velocityY = ${velocityY.value}\n"
        //"collision = ${checkCollisionJump(playerX = playerX.value, playerY =  playerY.value, velocityY =  velocityY.value,platforms = platforms, screenHeight = screenHeight)}\n" +
        //"playerYScreenHeight = ${playerY.value - screenHeight}\n"
        /*"platforms = $platforms\n" +
        "platform[0] = ${platforms[0].y + screenHeight}\n" +
        "platform[1] = ${platforms[1].y + screenHeight}\n" +
        "platform[2] = ${platforms[2].y + screenHeight}\n" +
        "platform[3] = ${platforms[3].y + screenHeight}\n"*/,
        modifier = Modifier.padding(top = 40.dp)
    )
}

@Composable
fun MoveRight(playerX: MutableState<Float>) {
    Button(
        onClick = {
            playerX.value += 20f // Перемещаем игрока вправо на 20 единиц
        },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text("Move Right")
    }
}

@Composable
fun MoveLeft(playerX: MutableState<Float>) {
    Button(
        onClick = {
            playerX.value -= 20f // Перемещаем игрока вправо на 20 единиц
        },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text("Move Left")
    }
}

@Composable
fun UpdateMaxY(playerY: Float, maxY: MutableState<Float>) {
    if (playerY > -maxY.value) {
        maxY.value = playerY
    }
}

@Composable
fun CalculateScore(playerY: Float, maxY: MutableState<Float>, screenHeight: Float): Int {
    // Пример расчета очков: для каждой единицы поднятия по Y даем 1 очко
    return (maxY.value.toInt())
}

@Composable
fun ScoreDisplay(score: MutableState<Int>) {
    Text(
        text = "Score: ${score.value}",
        fontFamily = doodleFontFamily,
        fontSize = 20.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier
            .padding(start = 8.dp)
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
fun PauseGame() {
    val pauseImage = painterResource(R.drawable.pause_screen)
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = {},
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

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    DoodleJumpTheme {
        DoodleJumpGameScreen()
    }
}