package com.example.doodlejump.data

enum class BonusType {
    NONE,
    SPRING,
    HELICOPTER
}

enum class PlatformType {
    STANDING,
    MOVING
}

data class Platform(
    var x: Float,
    var y: Float,
    var platformType: PlatformType = PlatformType.STANDING,
    var movingSpeed: Float = 0f,
    var bonusType: BonusType = BonusType.NONE,
    var movingDirection: Int = 1
)