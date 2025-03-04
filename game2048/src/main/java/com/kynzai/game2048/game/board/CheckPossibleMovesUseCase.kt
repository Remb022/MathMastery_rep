package com.kynzai.game2048.game.board

import com.kynzai.game2048.datastore.DEFAULT_VALUE

class CheckPossibleMovesUseCase {

    fun checkMovesToContinue(
        boardGame: MutableList<MutableList<Int>>,
        numberToWin: Int,
        currentScore: Int = 0
    ): MoveNumberResult {
        println("4.1 [CheckPossibleMoves] Received score: $currentScore")

        val playingBoardGame = MoveNumberResult(
            boardGame = boardGame,
            numberToWin = numberToWin,
            score = currentScore
        )

        for (row in boardGame) {
            if (row.contains(DEFAULT_VALUE)) {
                return playingBoardGame
            }
        }

        val size = boardGame.size
        for (i in 0 until size) {
            for (j in 0 until size) {
                val currentCell = boardGame[i][j]

                if (j + 1 < size && (boardGame[i][j + 1] == DEFAULT_VALUE || boardGame[i][j + 1] == currentCell)) {
                    return playingBoardGame
                }
                if (i + 1 < size && (boardGame[i + 1][j] == DEFAULT_VALUE || boardGame[i + 1][j] == currentCell)) {
                    return playingBoardGame
                }
            }
        }

        // Если ни одного хода нет, возвращаем GAME_OVER
        return MoveNumberResult(
            boardGame = boardGame,
            gameStatus = GameStatus.GAME_OVER,
            numberToWin = numberToWin,
            score = currentScore // Передаем текущий счет
        )
    }
}
