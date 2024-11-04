package com.example.doodlejump

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doodlejump.ui.theme.doodleFontFamily

@Composable
fun ScoreDisplay(score: MutableIntState, platformShift: Float) {
    calculateScore(platformShift = platformShift, score = score)
    Text(
        text = "Score: ${score.intValue}",
        fontFamily = doodleFontFamily,
        fontSize = 20.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier
            .padding(start = 8.dp)
    )
}

@Composable
fun calculateScore(platformShift: Float, score: MutableState<Int>): Int {
    val lastPlatformShift = remember { mutableFloatStateOf(0f) }
    if (platformShift < lastPlatformShift.floatValue) {
        score.value += (lastPlatformShift.floatValue - platformShift).toInt()
        lastPlatformShift.floatValue = platformShift
    }
    return (score.value)
}