package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun PlayerCharacter(playerX: Float, playerY: Float, modifier: Modifier = Modifier) {
    /*var playerHeight by remember { mutableStateOf(60f) }
    var playerWidth by remember { mutableStateOf(60f)}*/
    val modelRight = painterResource(R.drawable.lik_right)
    val modelLeft = painterResource(R.drawable.lik_left)
    Box(
        modifier = Modifier
            .offset(x = playerX.dp, y = playerY.dp)
    ) {
        Image(
            painter = modelRight,
            contentDescription = "Player",
            modifier = modifier
                /*.onGloballyPositioned { coordinates ->
                    playerHeight = with(density) { coordinates.size.height.toFloat() }
                    playerWidth = with(density) { coordinates.size.width.toFloat() }
                }*/
                .size(playerWidth.dp, playerHeight.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}
