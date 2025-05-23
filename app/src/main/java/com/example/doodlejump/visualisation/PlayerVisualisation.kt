package com.example.doodlejump.ui.visuals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doodlejump.R
import com.example.doodlejump.data.Direction
import com.example.doodlejump.data.playerHeight
import com.example.doodlejump.data.playerWidth
import com.example.doodlejump.models.GameViewModel

@Composable
fun PlayerVisualisation(
    playerX: Float,
    playerY: Float,
    viewModel: GameViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val modelRight = painterResource(R.drawable.lik_right)
    val modelLeft = painterResource(R.drawable.lik_left)
    val currentModel =
        if (viewModel.playerDirection.value == Direction.RIGHT) modelRight else modelLeft

    Box(
        modifier = Modifier.offset(x = playerX.dp, y = playerY.dp)
    ) {
        Image(
            painter = currentModel,
            contentDescription = "Player",
            modifier = modifier.size(playerWidth.dp, playerHeight.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}
