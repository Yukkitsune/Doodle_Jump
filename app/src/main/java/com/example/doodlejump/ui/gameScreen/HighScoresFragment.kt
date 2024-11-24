package com.example.doodlejump.ui.gameScreen

import HighScoresAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doodlejump.R
import com.example.doodlejump.databinding.FragmentHighScoresBinding
import com.example.doodlejump.models.HighScoresViewModel

class HighScoresFragment : Fragment(R.layout.fragment_high_scores) {

    private var _binding: FragmentHighScoresBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HighScoresViewModel by viewModels()
    private lateinit var highScoresAdapter: HighScoresAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHighScoresBinding.bind(view)

        setupRecyclerView()
        observeViewModel()
        viewModel.loadHighScores(requireContext())
    }

    private fun setupRecyclerView() {
        highScoresAdapter = HighScoresAdapter()
        binding.rvHighScores.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = highScoresAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.highScores.observe(viewLifecycleOwner) { highScores ->
            highScoresAdapter.submitList(highScores)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}