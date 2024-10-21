package com.example.doodlejump

import android.app.GameState

data class DoodleJumpGameState(
    val direction: Direction = Direction.RIGHT,
    val isGameOver: Boolean = false,
    var gameState: GameStatus = GameStatus.IDLE

    )

enum class GameStatus{
    IDLE,
    STARTED,
    PAUSED,
    GAMEOVER
}
enum class Direction{
    LEFT,
    RIGHT
}