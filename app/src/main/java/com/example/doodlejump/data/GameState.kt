package com.example.doodlejump.data

data class DoodleJumpGameState(
    val direction: Direction = Direction.RIGHT,
    val isGameOver: Boolean = false,
    var gameState: GameStatus = GameStatus.IDLE,
    val score: Int = 0

)

enum class GameStatus {
    IDLE,
    STARTED,
    GAMEOVER
}

enum class Direction {
    LEFT,
    RIGHT
}