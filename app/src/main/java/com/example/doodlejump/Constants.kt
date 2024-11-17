package com.example.doodlejump


const val GRAVITY = 0.8f // Гравитация
const val JUMP_FORCE = -20f // Сила прыжка
const val SPRING_JUMP_FORCE = -30f
var playerHeight = 60f
var playerWidth = 60f
var platformHeight = 20f
var platformWidth = 100f
var platformShift = 0f
val initialPlatforms = listOf(
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
