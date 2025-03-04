package com.kynzai.game2048.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kynzai.game2048.datastore.DataStoreManager
import com.kynzai.game2048.game.board.CreateBoardGameUseCase
import com.kynzai.game2048.game.board.DEFAULT_NUMBER_TO_WIN
import com.kynzai.game2048.datastore.GameState
import com.kynzai.game2048.game.board.GameStatus
import com.kynzai.game2048.game.logic.MoveNumbersUseCase
import com.kynzai.game2048.game.logic.MovementDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwoZeroFourEightViewModel @Inject constructor(
    private val boardGameUseCases: CreateBoardGameUseCase,
    private val moveNumbersUseCase: MoveNumbersUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    init {
        loadGameState()
    }

    private fun loadGameState() = viewModelScope.launch {
        dataStoreManager.gameStateFlow.collect { savedState ->
            _gameState.value = savedState
        }
    }

    //TODO: Размер поля
    fun startNewGame() = viewModelScope.launch {
        val newBoard = boardGameUseCases.createBoardGame(4)
        _gameState.update {
            it.copy(
                board = newBoard,
                gameStatus = GameStatus.PLAYING,
                numberToWin = DEFAULT_NUMBER_TO_WIN,
                currentScore = 0
            )
        }
        saveGameState()
    }

    fun moveNumbers(direction: MovementDirection) = viewModelScope.launch {
        if (direction == MovementDirection.NONE) return@launch

        val currentState = _gameState.value
        val newBoardResult = moveNumbersUseCase.moveNumbers(
            currentState.board,
            direction,
            currentState.gameStatus,
            currentState.numberToWin,
            currentState.currentScore
        )

        _gameState.update {
            it.copy(
                board = newBoardResult.boardGame,
                gameStatus = newBoardResult.gameStatus,
                numberToWin = newBoardResult.numberToWin,
                currentScore = it.currentScore + newBoardResult.score
            )
        }

        saveGameState()

        if (newBoardResult.gameStatus == GameStatus.GAME_OVER) {
            saveRecord(_gameState.value.currentScore)
        }
    }

    private fun saveRecord(score: Int) = viewModelScope.launch {
        dataStoreManager.saveHighScore(score)
    }

    private fun saveGameState() = viewModelScope.launch {
        val state = _gameState.value
        dataStoreManager.saveGameState(
            state.board,
            state.currentScore,
            state.gameStatus.ordinal
        )
    }
}