package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random


data class Platform(

    val x: Float,
    val y: Float
)

@Composable
fun Platforms(screenWidth: Float, screenHeight: Float, modifier: Modifier): MutableList<Platform> {
    val platformImage = painterResource(R.drawable.greenbar)
    val screenHeightDP = LocalConfiguration.current.screenHeightDp.dp
    var platforms = initialPlatforms
    //platforms += generateRandomPlatforms(screenWidth,screenHeight, count = 10)
    platforms.forEach { platform ->
        Box(
            modifier = Modifier
                //.background(Color.Black)
                .size(platformWidth.dp, platformHeight.dp)
                .offset(x = platform.x.dp, y = (screenHeightDP - platform.y.dp))
        ) {
            Image(
                painter = platformImage,
                contentDescription = "Platform",
                modifier = Modifier
                    .size(platformWidth.dp, platformHeight.dp)
            )
        }
    }
    return platforms
}

fun generateRandomPlatforms(
    screenWidth: Float,
    screenHeight: Float,
    count: Int
): MutableList<Platform> {

    val platforms = mutableListOf<Platform>()
    for (i in 0 until count) {
        val x = Random.nextFloat() * screenWidth
        val y = Random.nextFloat() * screenHeight
        platforms.add(Platform(x, y))
    }
    return platforms
}

fun updatePlatforms(
    playerY: Float,
    platforms: MutableList<Platform>,
    screenWidth: Float,
    screenHeight: Float,
    platformGap: Float
) {
    if (platforms.isNotEmpty() && playerY < screenHeight - platforms.maxOf { it.y }) {
        val newPlatformY = platforms.maxOf { it.y } + platformGap
        val newPlatformX = Random.nextFloat() * screenWidth
        platforms.removeAt(0)
        platforms.add(Platform(newPlatformX, screenHeight - newPlatformY))
    }

}

