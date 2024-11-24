package com.example.doodlejump.ui.components

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.doodlejump.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun playSoundAsync(mediaPlayer: MediaPlayer) = CoroutineScope(Dispatchers.IO).launch {
    if (!mediaPlayer.isPlaying) {
        mediaPlayer.start()
    }
}

@Composable
fun setupMediaPlayer(): Map<String, MediaPlayer> {
    val context = LocalContext.current

    val mediaPlayer = remember {
        mapOf(
            "jump" to MediaPlayer().apply {
                setDataSource(
                    context,
                    android.net.Uri.parse("android.resource://${context.packageName}/${R.raw.jump}")
                )
                prepare()
            },
            "spring" to MediaPlayer().apply {
                setDataSource(
                    context,
                    android.net.Uri.parse("android.resource://${context.packageName}/${R.raw.spring}")
                )
                prepare()
            },
            "gameover" to MediaPlayer().apply {
                setDataSource(
                    context,
                    android.net.Uri.parse("android.resource://${context.packageName}/${R.raw.gameover}")
                )
                prepare()
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.values.forEach { player ->
                if (player.isPlaying) {
                    player.stop()
                }
                player.release()
            }
        }
    }

    return mediaPlayer
}