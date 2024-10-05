package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp


@Composable
fun PlayerCharacter(playerY: Float,modifier: Modifier = Modifier){
    val density = LocalDensity.current
    val playerYOffset: Dp = with(density){
        playerY.toDp()
    }
    val modelRight = painterResource(R.drawable.lik_right)
    val modelLeft = painterResource(R.drawable.lik_left)
    Image(
        painter = modelRight,
        contentDescription = "Player",
        modifier = modifier
            .offset(y = playerYOffset)
            .size(60.dp),
        contentScale = ContentScale.Crop
    )
}