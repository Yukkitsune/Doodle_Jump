package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun PlayerCharacter(playerX: Float, playerY: Float,viewModel: GameViewModel = viewModel(), modifier: Modifier = Modifier) {
    val modelRight = painterResource(R.drawable.lik_right)
    val modelLeft = painterResource(R.drawable.lik_left)
    val currentModel = if( viewModel.playerDirection.value == Direction.RIGHT){
        modelRight
    }else{
        modelLeft
    }
    Box(
        modifier = Modifier
            .offset(x = playerX.dp, y = playerY.dp)
    ) {
        Image(
            painter = currentModel,
            contentDescription = "Player",
            modifier = modifier
                .size(playerWidth.dp, playerHeight.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}
@Composable
fun PlayerDisplay(playerX: MutableState<Float>, playerY: MutableState<Float>, modifier:Modifier = Modifier){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PlayerCharacter(playerX = playerX.value, playerY = playerY.value, modifier = Modifier )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            MoveRight(playerX.value)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            MoveLeft(playerX.value)
        }
    }
}