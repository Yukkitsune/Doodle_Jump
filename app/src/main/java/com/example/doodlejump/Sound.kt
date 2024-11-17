package com.example.doodlejump

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun setupMediaPlayer():Map<String,MediaPlayer>{
    val context = LocalContext.current
    val mediaPlayer = remember {
        mapOf(
            "jump" to MediaPlayer.create(context, R.raw.jump),
            "spring" to MediaPlayer.create(context, R.raw.spring),
            "gameover" to MediaPlayer.create(context, R.raw.gameover)
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.values.forEach{it.release()}
        }
    }
    return mediaPlayer
}