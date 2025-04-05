package com.kynzai.game2048.datastore

import com.kynzai.game2048.game.board.DEFAULT_NUMBER_TO_WIN
import com.kynzai.game2048.game.board.GameStatus


const val DEFAULT_VALUE = -1

data class GameState(
    val board: MutableList<MutableList<Int>> = mutableListOf(),
    val gameStatus: GameStatus = GameStatus.PLAYING,
    val numberToWin: Int = DEFAULT_NUMBER_TO_WIN,
    val currentScore: Int = 0,
    val highScore: Int = 0,
)
