package com.example.doodlejump.ui.gameScreen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.doodlejump.R
import com.example.doodlejump.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)
        val sharedPreferences =
            requireContext().getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.soundSwitch.isChecked = sharedPreferences.getBoolean("sound_enabled", true)
        binding.musicSwitch.isChecked = sharedPreferences.getBoolean("music_enabled", true)

        binding.soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("sound_enabled", isChecked).apply()
        }
        binding.musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("music_enabled", isChecked).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}