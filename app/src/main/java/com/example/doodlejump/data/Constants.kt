package com.example.doodlejump.data

import kotlinx.coroutines.sync.Mutex


const val GRAVITY = 0.8f // Гравитация
const val JUMP_FORCE = -20f // Сила прыжка
const val SPRING_JUMP_FORCE = -30f
const val HELICOPTER_FLY_FORCE = -3f
const val playerHeight = 60f
const val playerWidth = 60f
const val platformHeight = 20f
const val platformWidth = 100f
var platformShift = 0f
val platformsMutex = Mutex()
val initialPlatforms = listOf(
    Platform(0f, 10f),
    Platform(40f, 250f),
    Platform(80f, 20f),
    Platform(160f, 0f),
    Platform(160f, 180f),
    Platform(240f, 15f),
    Platform(320f, 20f),
    Platform(265f, 240f),
    Platform(105f, 380f),
    Platform(0f, 340f)
)
