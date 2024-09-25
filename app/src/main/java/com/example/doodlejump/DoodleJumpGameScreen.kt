package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doodlejump.ui.theme.DoodleJumpTheme
import com.example.doodlejump.ui.theme.doodleFontFamily

@Composable
fun DoodleJumpGameScreen(

    //state: DoodleGamwState,
    //onEvent: (DoodleJumpGameEvent) -> Unit,
    modifier: Modifier = Modifier)
{
    InterfaceImage()
}
@Composable
fun InterfaceImage(){
    val backgroundImage = painterResource(R.drawable.background_1x)
    val scoreImage = painterResource(R.drawable.score_1x)
    val pauseImage = painterResource(R.drawable.pause_screen)
    Box(modifier = Modifier
        .fillMaxSize()){
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
        Text(
            text = "Score: ",
            fontFamily = doodleFontFamily,
            fontSize = 20.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(start = 8.dp)
        )
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
        PlayerModel(playerY = 3f, modifier = Modifier.align(Alignment.TopCenter))

    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    DoodleJumpTheme {
        DoodleJumpGameScreen()
    }
}