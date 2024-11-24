package com.example.doodlejump.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doodlejump.ui.theme.doodleFontFamily

@Composable
fun ScoreDisplay(score: MutableState<Int>, platformShift: Float) {
    UpdateScore(platformShift = platformShift, score = score)

    Text(
        text = "Score: ${score.value}",
        fontFamily = doodleFontFamily,
        fontSize = 20.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Composable
fun UpdateScore(platformShift: Float, score: MutableState<Int>) {
    val lastPlatformShift = remember { mutableStateOf(platformShift) }
    if (platformShift < lastPlatformShift.value) {
        score.value += (lastPlatformShift.value - platformShift).toInt()
    }

    lastPlatformShift.value = platformShift
}