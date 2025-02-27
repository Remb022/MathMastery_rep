package com.kynzai.game2048.game.logic

import com.kynzai.game2048.game.board.GameStatus
import com.kynzai.game2048.game.board.MoveNumberResult

class HasWonTheGameUseCase {

    fun checkIfHasWonTheGame(
        result: MoveNumberResult,
        nextHighNumber: Int
    ): MoveNumberResult {

        if (result.gameStatus != GameStatus.PLAYING) return result

        val hasWon = result.boardGame.any { row -> row.contains(nextHighNumber) }
        if (!hasWon) return result

        return result.copy(
            gameStatus = GameStatus.YOU_WIN,
            numberToWin = nextHighNumber * 2
            // score остается без изменений
        )
    }
}