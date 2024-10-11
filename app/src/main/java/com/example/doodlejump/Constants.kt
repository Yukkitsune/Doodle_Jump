package com.example.doodlejump

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

var playerHeight = 60f
var playerWidth = 60f
var platformHeight = 20f
var platformWidth = 100f

var initialPlatforms = mutableListOf(
    Platform(0f, 10f),
    Platform(80f, 20f),
    Platform(160f, 0f),
    Platform(160f, 180f),
    Platform(240f, 15f),
    Platform(320f, 20f),
    Platform(265f, 240f),
    Platform(105f, 380f),
    Platform(0f, 340f)
)
val bottomPlatformY = initialPlatforms.minByOrNull { it.y }?.y ?: 0f
val bottomPlatformX = initialPlatforms.minByOrNull { it.y }?.x ?: 0f
