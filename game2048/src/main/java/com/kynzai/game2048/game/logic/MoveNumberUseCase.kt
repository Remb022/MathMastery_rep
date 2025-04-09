package com.kynzai.game2048.game.logic

import com.kynzai.game2048.game.board.AddNumberToBoardGameUseCase
import com.kynzai.game2048.game.board.CheckPossibleMovesUseCase
import com.kynzai.game2048.game.board.GameStatus
import com.kynzai.game2048.game.board.MoveNumberResult
import javax.inject.Inject

class MoveNumbersUseCase @Inject constructor(
    private val combineAndMoveUseCase: CombineAndMoveNumbersUseCase,
    private val addNumberUseCase: AddNumberToBoardGameUseCase,
    private val possibleMovesUseCase: CheckPossibleMovesUseCase,
    private val hasWonUseCase: HasWonTheGameUseCase,
) {
    fun moveNumbers(
        boardGame: MutableList<MutableList<Int>>,
        movementDirection: MovementDirection,
        gameStatus: GameStatus,
        nextHighNumber: Int,
        currentScore: Int
    ): MoveNumberResult {

        println(" [MoveNumbers] START")
        println(" Direction: ${movementDirection.name}")
        println(" Current score: $currentScore")
        println(" Board before move:")
        boardGame.forEach { println(" $it") }

        val combineResult = combineAndMoveUseCase.combineAndMove(
            boardGame,
            movementDirection
        )

        if (combineResult == null) {
            println("[MoveNumbers] Board NOT changed")
            println(" Return score: $currentScore")
            return MoveNumberResult(
                boardGame = boardGame,
                gameStatus = gameStatus,
                numberToWin = nextHighNumber,
                score = 0
            )
        }

        val (boardAfterMove, earnedScore) = combineResult
        println(" [MoveNumbers] Board changed:")
        boardAfterMove.forEach { println("   $it") }
        println(" Earned score: $earnedScore")

        val updatedScore = earnedScore

        val boardAfterNewNumber = addNumberUseCase.addNumber(boardAfterMove)
        println(" Board after adding number:")
        boardAfterNewNumber.forEach { println("   $it") }

        val moveResult = possibleMovesUseCase.checkMovesToContinue(
            boardAfterNewNumber,
            nextHighNumber,
            updatedScore
        )
        println(" [CheckPossibleMoves] Returned score: ${moveResult.score}")

        val finalResult = hasWonUseCase.checkIfHasWonTheGame(
            moveResult.copy(score = updatedScore),
            nextHighNumber
        )
        println(" [HasWon] Final score: ${finalResult.score}")

        return finalResult
    }

}