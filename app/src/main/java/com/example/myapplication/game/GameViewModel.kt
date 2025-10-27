package com.example.myapplication.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    
    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState
    
    private val _boardSize = MutableLiveData<Int>()
    val boardSize: LiveData<Int> = _boardSize
    
    private val _currentPlayer = MutableLiveData<Player>()
    val currentPlayer: LiveData<Player> = _currentPlayer
    
    private val _gameBoard = MutableLiveData<Array<Array<Player?>>>()
    val gameBoard: LiveData<Array<Array<Player?>>> = _gameBoard
    
    private val _winner = MutableLiveData<Player?>()
    val winner: LiveData<Player?> = _winner
    
    private val _isGameOver = MutableLiveData<Boolean>()
    val isGameOver: LiveData<Boolean> = _isGameOver
    
    private val _isDraw = MutableLiveData<Boolean>()
    val isDraw: LiveData<Boolean> = _isDraw
    
    fun initializeGame(size: Int) {
        if (size < 3 || size > 5) return
        
        _boardSize.value = size
        _currentPlayer.value = Player.X
        _winner.value = null
        _isGameOver.value = false
        _isDraw.value = false
        
        val board = Array(size) { arrayOfNulls<Player?>(size) }
        _gameBoard.value = board
        _gameState.value = GameState.PLAYING
    }
    
    fun makeMove(row: Int, col: Int) {
        val board = _gameBoard.value ?: return
        val currentPlayerValue = _currentPlayer.value ?: return
        
        if (_isGameOver.value == true || board[row][col] != null) return
        
        board[row][col] = currentPlayerValue
        _gameBoard.value = board
        
        if (checkWin(row, col, currentPlayerValue)) {
            _winner.value = currentPlayerValue
            _isGameOver.value = true
            _gameState.value = GameState.WON
        } else if (isBoardFull()) {
            _isDraw.value = true
            _isGameOver.value = true
            _gameState.value = GameState.DRAW
        } else {
            _currentPlayer.value = if (currentPlayerValue == Player.X) Player.O else Player.X
        }
    }

    private fun checkWin(row: Int, col: Int, player: Player): Boolean {
        val board = _gameBoard.value ?: return false

        return checkRow(row, player, board) ||
                checkColumn(col, player, board) ||
                checkMainDiagonal(row, col, player, board) ||
                checkAntiDiagonal(row, col, player, board)
    }


    private fun isBoardFull(): Boolean {
        val board = _gameBoard.value ?: return false
        return board.all { row -> row.all { it != null } }
    }
    
    fun resetGame() {
        val size = _boardSize.value ?: 3
        initializeGame(size)
    }

    private fun checkRow(row: Int, player: Player, board: Array<Array<Player?>>): Boolean {
        var count = 0
        for (i in board[row].indices) {
            if (board[row][i] == player) count++ else count = 0
            if (count == 3) return true
        }
        return false
    }

    private fun checkColumn(col: Int, player: Player, board: Array<Array<Player?>>): Boolean {
        var count = 0
        for (i in board.indices) {
            if (board[i][col] == player) count++ else count = 0
            if (count == 3) return true
        }
        return false
    }

    private fun checkMainDiagonal(row: Int, col: Int, player: Player, board: Array<Array<Player?>>): Boolean {
        val size = board.size
        var count = 0
        val startRow = maxOf(0, row - col)
        val startCol = maxOf(0, col - row)

        for (i in 0 until minOf(size - startRow, size - startCol)) {
            if (board[startRow + i][startCol + i] == player) count++ else count = 0
            if (count == 3) return true
        }
        return false
    }

    private fun checkAntiDiagonal(row: Int, col: Int, player: Player, board: Array<Array<Player?>>): Boolean {
        val size = board.size
        var count = 0
        val antiStartRow = minOf(size - 1, row + col)
        val antiStartCol = maxOf(0, col - (size - 1 - row))

        for (i in 0 until minOf(antiStartRow + 1, size - antiStartCol)) {
            if (board[antiStartRow - i][antiStartCol + i] == player) count++ else count = 0
            if (count == 3) return true
        }
        return false
    }

}

enum class Player {
    X, O
}

enum class GameState {
    PLAYING, WON, DRAW
}

