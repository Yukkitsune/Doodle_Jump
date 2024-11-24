package com.example.doodlejump.ui.gameScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.doodlejump.R
import com.example.doodlejump.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMenuBinding.bind(view)

        binding.btnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
        }
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
        }
        binding.btnHighScores.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_highScoresFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}