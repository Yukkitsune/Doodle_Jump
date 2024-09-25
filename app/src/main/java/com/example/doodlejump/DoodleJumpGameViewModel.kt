package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

@Composable
fun PlayerModel(playerY:Float, modifier: Modifier){
    val modelRight = painterResource(R.drawable.lik_right)
    val modelLeft = painterResource(R.drawable.lik_left)
    Image(
        painter = modelRight,
        contentDescription = "Right player model",
        modifier = Modifier
            .size(60.dp),

        contentScale = ContentScale.Crop
    )
}