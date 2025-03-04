package com.kynzai.game2048.game.logic

import com.kynzai.game2048.datastore.DEFAULT_VALUE

class CombineAndMoveNumbersUseCase {

    fun combineAndMove(
        boardGameOriginal: MutableList<MutableList<Int>>,
        movementDirection: MovementDirection
    ): Pair<MutableList<MutableList<Int>>, Int>? {
        val boardSize = boardGameOriginal.size
        val boardGame = boardGameOriginal.copy()

        val (boardGameAfterMove, earnedScore) = when (movementDirection) {
            MovementDirection.LEFT, MovementDirection.RIGHT -> {
                getLeftRightMove(boardSize, boardGame, movementDirection)
            }
            MovementDirection.UP, MovementDirection.DOWN -> {
                getUpDownMove(boardSize, boardGame, movementDirection)
            }
            MovementDirection.NONE -> return null // Если направление не указано, возвращаем null
        }
        println("Original board: $boardGameOriginal")
        println("Moved board: $boardGameAfterMove")
        println("Earned score: $earnedScore")

        // Если доска не изменилась, возвращаем null
        return if (boardGameOriginal.isTheSameBoard(boardGameAfterMove)) {
            null
        } else {
            Pair(boardGameAfterMove, earnedScore)

        }
    }


    private fun getLeftRightMove(
        boardSize: Int,
        boardGame: MutableList<MutableList<Int>>,
        movementDirection: MovementDirection
    ): Pair<MutableList<MutableList<Int>>, Int> {
        val newBoard = boardGame.map { it.toMutableList() }.toMutableList()
        var earnedScore = 0 // Очки за этот ход

        for (rowIndex in 0 until boardSize) {
            val row = newBoard[rowIndex].toMutableList()
            row.removeAll { it == DEFAULT_VALUE }

            val newRow = mutableListOf<Int>()
            if (movementDirection == MovementDirection.RIGHT) {
                row.reverse()
            }

            var i = 0
            while (i < row.size) {
                if (i + 1 < row.size && row[i] == row[i + 1]) {
                    val combinedValue = row[i] * 2
                    newRow.add(combinedValue)
                    earnedScore += combinedValue
                    i += 2
                } else {
                    newRow.add(row[i])
                    i += 1
                }
            }

            while (newRow.size < boardSize) {
                newRow.add(DEFAULT_VALUE)
            }

            if (movementDirection == MovementDirection.RIGHT) {
                newRow.reverse()
            }

            newBoard[rowIndex] = newRow
        }

        return Pair(newBoard, earnedScore)
    }

    private fun getUpDownMove(
        boardSize: Int,
        boardGame: MutableList<MutableList<Int>>,
        movementDirection: MovementDirection
    ): Pair<MutableList<MutableList<Int>>, Int> {
        val newBoard = boardGame.map { it.toMutableList() }.toMutableList()
        var earnedScore = 0

        for (columnIndex in 0 until boardSize) {
            val column = mutableListOf<Int>()
            for (rowIndex in 0 until boardSize) {
                val cell = newBoard[rowIndex][columnIndex]
                if (cell != DEFAULT_VALUE) {
                    column.add(cell)
                }
            }

            val newColumn = mutableListOf<Int>()
            if (movementDirection == MovementDirection.DOWN) {
                column.reverse()
            }

            var i = 0
            while (i < column.size) {
                if (i + 1 < column.size && column[i] == column[i + 1]) {
                    val combinedValue = column[i] * 2
                    newColumn.add(combinedValue)
                    earnedScore += combinedValue
                    i += 2
                } else {
                    newColumn.add(column[i])
                    i += 1
                }
            }

            while (newColumn.size < boardSize) {
                newColumn.add(DEFAULT_VALUE)
            }

            if (movementDirection == MovementDirection.DOWN) {
                newColumn.reverse()
            }

            for (rowIndex in 0 until boardSize) {
                newBoard[rowIndex][columnIndex] = newColumn[rowIndex]
            }
        }

        return Pair(newBoard, earnedScore)
    }
}

fun MutableList<MutableList<Int>>.copy(): MutableList<MutableList<Int>> {
    val list = mutableListOf<MutableList<Int>>()
    for (rowIndex in 0..<size) {
        val subList = mutableListOf<Int>()
        for (cellIndex in 0..<size) {
            subList.add(this[rowIndex][cellIndex])
        }
        list.add(subList)
    }
    return list
}


fun MutableList<MutableList<Int>>.isTheSameBoard(other: MutableList<MutableList<Int>>): Boolean {
    for (rowIndex in 0..<size) {
        for (cellIndex in 0..<size) {
            if (this[rowIndex][cellIndex] != other[rowIndex][cellIndex]) {
                return false
            }
        }
    }
    return true
}

