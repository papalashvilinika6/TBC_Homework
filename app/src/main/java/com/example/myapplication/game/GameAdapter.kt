package com.example.myapplication.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemGameButtonBinding
import com.example.myapplication.R

class GameAdapter(
    private val onButtonClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameButtonViewHolder>() {
    
    private var boardSize = 3
    private var gameBoard: Array<Array<Player?>> = Array(3) { arrayOfNulls(3) }
    private var isGameOver = false
    
    fun updateBoard(board: Array<Array<Player?>>, size: Int, gameOver: Boolean) {
        this.gameBoard = board
        this.boardSize = size
        this.isGameOver = gameOver
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameButtonViewHolder {
        val binding = ItemGameButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameButtonViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: GameButtonViewHolder, position: Int) {
        val row = position / boardSize
        val col = position % boardSize
        val player = gameBoard[row][col]
        
        holder.bind(player, isGameOver) {
            onButtonClick(row, col)
        }
    }
    
    override fun getItemCount(): Int = boardSize * boardSize
    
    class GameButtonViewHolder(
        private val binding: ItemGameButtonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(player: Player?, isGameOver: Boolean, onClick: () -> Unit) {
            binding.imageButton.apply {
                when (player) {
                    Player.X -> {
                        setImageResource(R.drawable.ic_x)
                        isEnabled = false
                    }
                    Player.O -> {
                        setImageResource(R.drawable.ic_o)
                        isEnabled = false
                    }
                    null -> {
                        setImageResource(0)
                        isEnabled = !isGameOver
                    }
                }
                
                setOnClickListener {
                    if (player == null && !isGameOver) {
                        onClick()
                    }
                }
            }
        }
    }
}
