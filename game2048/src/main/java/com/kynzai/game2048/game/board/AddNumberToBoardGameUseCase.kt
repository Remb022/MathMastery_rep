package com.kynzai.game2048.game.board

import com.kynzai.game2048.datastore.DEFAULT_VALUE
import kotlin.random.Random

private val NUMBER_OPTIONS = listOf(2, 2, 2, 2, 4)


class AddNumberToBoardGameUseCase(

) {
    fun addNumber(boardGame: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        val position = findAvailablePosition(boardGame) ?: return boardGame
        val number = getNumber()

        boardGame[position.first][position.second] = number

        return boardGame
    }

    private fun findAvailablePosition(boardGame: MutableList<MutableList<Int>>): Pair<Int, Int>? {
        val lineSize = boardGame.size
        val availableCells = mutableListOf<Pair<Int, Int>>()

        for (rowIndex in 0..<lineSize) {
            for (cellIndex in 0..<lineSize) {
                val cell = boardGame[rowIndex][cellIndex]
                if (cell == DEFAULT_VALUE) {
                    availableCells.add(Pair(rowIndex, cellIndex))
                }
            }
        }

        if (availableCells.isEmpty()) {
            return null
        }

        val position = Random.nextInt(availableCells.size)
        return availableCells[position]
    }

    private  fun  getNumber () : Int {
        val randomIndex = Random.nextInt(NUMBER_OPTIONS.size)
        return NUMBER_OPTIONS[randomIndex]
    }
}
