package com.example.myapplication.game

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentGameBinding

class GameFragment : BaseFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {
    
    private val viewModel: GameViewModel by activityViewModels()
    private val args: GameFragmentArgs by navArgs()
    private lateinit var gameAdapter: GameAdapter
    
    override fun listeners() {
        btnReset()
        btnBack()
    }
    
    override fun bind() {
        setupRecyclerView()
        setupObservers()

        viewModel.initializeGame(args.boardSize)
    }

    private fun btnBack(){
        binding.btnBackToHomeId.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun btnReset(){
        binding.btnResetGameId.setOnClickListener {
            resetGame()
        }
    }
    
    private fun setupRecyclerView() {
        gameAdapter = GameAdapter { row, col ->
            viewModel.makeMove(row, col)
        }
        
        binding.recyclerViewGameId.apply {
            layoutManager = GridLayoutManager(requireContext(), args.boardSize)
            adapter = gameAdapter
        }
    }
    
    private fun setupObservers() = with(binding) {
        viewModel.gameBoard.observe(viewLifecycleOwner) { board ->
            if (::gameAdapter.isInitialized) {
                gameAdapter.updateBoard(board, args.boardSize, viewModel.isGameOver.value ?: false)
            }
        }
        
        viewModel.currentPlayer.observe(viewLifecycleOwner) { player ->
            textGameStatusId.text = getString(R.string.current_player, player.name)
        }
        
        viewModel.winner.observe(viewLifecycleOwner) { winner ->
            if (winner != null) {
                textGameStatusId.text = getString(R.string.player_wins, winner.name)
                textGameStatusId.visibility = View.VISIBLE
            }
        }
        
        viewModel.isDraw.observe(viewLifecycleOwner) { isDraw ->
            if (isDraw) {
                textGameStatusId.text = getString(R.string.game_draw)
                textGameStatusId.visibility = View.VISIBLE
            }
        }
    }
    
    private fun resetGame() {
        viewModel.resetGame()
        binding.textGameStatusId.visibility = View.GONE
    }
}