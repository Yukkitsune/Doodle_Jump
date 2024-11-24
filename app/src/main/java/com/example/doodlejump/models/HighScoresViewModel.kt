package com.example.doodlejump.models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doodlejump.data.HighScore

class HighScoresViewModel : ViewModel() {

    private val _highScores = MutableLiveData<List<HighScore>>()
    val highScores: LiveData<List<HighScore>> get() = _highScores

    fun loadHighScores(context: Context) {
        val sharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val highScoresSet =
            sharedPreferences.getStringSet("high_scores", mutableSetOf()) ?: mutableSetOf()

        val scores = highScoresSet.map { entry ->
            val parts = entry.split(": ")
            HighScore(parts[0], parts[1].toInt())
        }
        _highScores.value = scores
    }

    fun addHighScore(playerName: String, score: Int, context: Context) {
        val sharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val highScoresSet =
            sharedPreferences.getStringSet("high_scores", mutableSetOf()) ?: mutableSetOf()

        val highScoreEntry = "$playerName: $score"
        highScoresSet.add(highScoreEntry)

        sharedPreferences.edit()
            .putStringSet("high_scores", highScoresSet)
            .apply()

        loadHighScores(context)
    }
}