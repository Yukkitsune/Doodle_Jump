package com.example.doodlejump

data class DoodleJumpGameState(
    val direction: Direction = Direction.RIGHT,

    )

enum class GameState{
    IDLE,
    STARTED,
    PAUSED
}
enum class Direction{
    LEFT,
    RIGHT
}