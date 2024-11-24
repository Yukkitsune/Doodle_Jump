package com.example.doodlejump.ui.gameScreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.doodlejump.R
import com.example.doodlejump.databinding.FragmentGameOverBinding
import com.example.doodlejump.models.HighScoresViewModel

class GameOverFragment : Fragment(R.layout.fragment_game_over) {
    private val highScoresViewModel: HighScoresViewModel by activityViewModels()
    private var _binding: FragmentGameOverBinding? = null
    private val binding get() = _binding!!

    private var score = 0
    private var bestScore = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val args = GameOverFragmentArgs.fromBundle(it)
            score = args.score
        }
        val sharedPreferences =
            requireContext().getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        bestScore = sharedPreferences.getInt("best_score", 0)
        if (score > bestScore) {
            bestScore = score
            sharedPreferences.edit().putInt("best_score", bestScore).apply()
        }


        binding.tvGameOver.text = "    Game Over!\nYour score: $score\nBest score: $bestScore"

        highScoresViewModel.loadHighScores(requireContext())



        binding.btnSubmitName.setOnClickListener {
            val playerName = binding.etPlayerName.text.toString().trim()
            if (playerName.isNotEmpty()) {
                highScoresViewModel.addHighScore(playerName, score, requireContext())
                Toast.makeText(context, "Score saved successfully!", Toast.LENGTH_SHORT).show()
            } else {
                highScoresViewModel.addHighScore("Player", score, requireContext())
                Toast.makeText(context, "Score saved successfully!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnMenu.setOnClickListener {
            findNavController().navigate(R.id.action_gameOverFragment_to_menuFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}