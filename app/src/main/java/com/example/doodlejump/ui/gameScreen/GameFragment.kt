package com.example.doodlejump.ui.gameScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.doodlejump.R
import com.example.doodlejump.data.DoodleJumpGameState
import com.example.doodlejump.data.GameStatus
import com.example.doodlejump.data.Platform
import com.example.doodlejump.data.platformShift
import com.example.doodlejump.databinding.FragmentGameBinding
import com.example.doodlejump.logic.updatePlatformsAsync
import com.example.doodlejump.models.GameViewModel
import com.example.doodlejump.movement.UpdatePlayerPosition
import com.example.doodlejump.movement.playerSetupY
import com.example.doodlejump.movement.playerVelocityX
import com.example.doodlejump.movement.playerVelocityY
import com.example.doodlejump.ui.components.ScoreDisplay
import com.example.doodlejump.ui.components.setupMediaPlayer
import com.example.doodlejump.ui.visuals.Platforms
import com.example.doodlejump.ui.visuals.PlayerVisualisation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import java.util.concurrent.CopyOnWriteArrayList

class GameFragment : Fragment(R.layout.fragment_game) {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameViewModel
    private val gameLogicScope = CoroutineScope(Dispatchers.Default + Job())
    private val currentGameState = mutableStateOf(DoodleJumpGameState())
    private var platforms: CopyOnWriteArrayList<Platform> = CopyOnWriteArrayList(
        listOf(
            Platform(-80f, 20f),
            Platform(0f, 10f),
            Platform(80f, 20f),
            Platform(160f, 30f),
            Platform(160f, 180f),
            Platform(240f, 15f),
            Platform(320f, 20f),
            Platform(265f, 240f),
            Platform(105f, 380f),
            Platform(0f, 340f),
            Platform(400f, 30f)
        )

    )

    private var score = mutableIntStateOf(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        currentGameState.value = currentGameState.value.copy(gameState = GameStatus.STARTED)

        binding.gameCanvas.setContent {
            StartGame()
        }
        viewModel.gameState.observe(viewLifecycleOwner) { gameState ->
            if (gameState.gameState == GameStatus.GAMEOVER) {
                val action =
                    GameFragmentDirections.actionGameFragmentToGameOverFragment(score.value)
                findNavController().navigate(action)
            }
        }
    }

    @Composable
    fun StartGame() {
        Box(modifier = Modifier.fillMaxSize()) {
            UpdateGame()
        }
    }

    @Composable
    fun UpdateGame() {
        val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
        val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
        val playerX = viewModel.playerXPosition
        val playerY = playerSetupY(platforms)
        val velocityX = playerVelocityX()
        val velocityY = playerVelocityY()

        PlayerVisualisation(playerX = playerX.value, playerY = playerY.value)
        UpdatePlayerPosition(
            playerX = playerX,
            playerY = playerY,
            velocityX = velocityX,
            velocityY = velocityY,
            platforms = platforms,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            onPlatformShift = { shift -> platformShift += shift },
            currentGame = currentGameState,
            mediaPlayer = setupMediaPlayer(),
            viewModel = viewModel,
            currentScore = score.value,
            modifier = Modifier
        )
        ScoreDisplay(score = score, platformShift = platformShift)
        updatePlatformsAsync(
            platforms = platforms,
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )
        Platforms(platforms = platforms)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        gameLogicScope.cancel()
    }
}