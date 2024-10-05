package com.example.doodlejump

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun Platforms(platforms: List<Platform>, modifier: Modifier){
    val platformImage = painterResource(R.drawable.greenbar)
    val screenHeightDP = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    var platforms = platforms
    platforms += generateRandomPlatforms(screenWidth,screenHeight, count = 10)
    platforms.forEach{ platform ->
        Box(modifier = Modifier
            .background(Color.Transparent)
            .size(100.dp,20.dp)
            .offset(x = platform.x.dp, y = (screenHeightDP - platform.y.dp))){
            Image(
                painter = platformImage,
                contentDescription = "Platform",
                modifier = Modifier
                    .size(100.dp,20.dp)
                )
        }
    }
}
fun generateRandomPlatforms(screenWidth: Float, screenHeight: Float,count: Int):List<Platform>{

    val platforms = mutableListOf<Platform>()
    for(i in 0 until count){
        val x = Random.nextFloat()*screenWidth
        val y = Random.nextFloat()*screenHeight
        platforms.add(Platform(x,y))
    }
    return platforms
}
fun updatePlatforms(playerY: Float, platforms: MutableList<Platform>, screenWidth: Float, platformGap: Float){
    if (platforms.isNotEmpty() && playerY > platforms.maxOf{it.y}){
        val newPlatformY = platforms.maxOf{it.y} + platformGap
        val newPlatformX = Random.nextFloat()*screenWidth
        platforms.removeAt(0)
        platforms.add(Platform(newPlatformX,newPlatformY))
    }

}
var initialPlatforms = listOf(
    Platform(0f,10f),
    Platform(80f,20f),
    Platform(160f,0f),
    Platform(240f,15f),
    Platform(320f,20f)
)
data class Platform(
    val x: Float,
    val y: Float
)