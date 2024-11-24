package com.example.doodlejump.ui.visuals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.doodlejump.R
import com.example.doodlejump.data.BonusType
import com.example.doodlejump.data.Platform
import com.example.doodlejump.data.PlatformType
import com.example.doodlejump.data.platformHeight
import com.example.doodlejump.data.platformWidth

@Composable
fun Platforms(
    platforms: List<Platform>
) {
    val greenPlatform = painterResource(R.drawable.greenbar)
    val bluePlatform = painterResource(R.drawable.bluebar)
    val springImage = painterResource(R.drawable.springtransformed)
    val helicopterImage = painterResource(R.drawable.helicopter)
    val screenHeightDP = LocalConfiguration.current.screenHeightDp.dp

    platforms.forEach { platform ->
        Box(
            modifier = Modifier
                .size(platformWidth.dp, platformHeight.dp)
                .offset(x = platform.x.dp, y = (screenHeightDP - platform.y.dp))
        ) {
            val platformImage =
                if (platform.platformType == PlatformType.STANDING) greenPlatform else bluePlatform
            Image(
                painter = platformImage,
                contentDescription = "Platform",
                modifier = Modifier.size(platformWidth.dp, platformHeight.dp)
            )
            if (platform.bonusType == BonusType.SPRING) {
                Image(
                    painter = springImage,
                    contentDescription = "Spring",
                    modifier = Modifier
                        .size(15.dp)
                        .offset(x = (platformWidth / 4).dp, y = (-10).dp)
                )
            } else if (platform.bonusType == BonusType.HELICOPTER) {
                Image(
                    painter = helicopterImage,
                    contentDescription = "Helicopter",
                    modifier = Modifier
                        .size(15.dp)
                        .offset(x = (platformWidth / 4).dp, y = (-10).dp)
                )
            }
        }
    }
}