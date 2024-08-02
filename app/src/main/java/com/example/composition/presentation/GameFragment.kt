package com.example.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entities.GameResult
import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level

class GameFragment : Fragment() {

    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("binding in GameFragment is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewNumber.setOnClickListener {
            launchGameFinished(GameResult(true, 1, 1, GameSettings(1 , 1, 1, 1)))
        }

        when (level) {
            Level.TEST -> launchTestGame()
            Level.EASY -> launchEasyGame()
            Level.NORMAL -> launchNormalGame()
            Level.HARD -> launchHardGame()
        }
    }

    private fun launchTestGame() {}
    private fun launchEasyGame() {}
    private fun launchNormalGame() {}
    private fun launchHardGame() {}

    private fun launchGameFinished(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let{
            level = it
        }
    }

    companion object {
        private const val KEY_LEVEL = "level"
        const val NAME = "game_fragment"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}