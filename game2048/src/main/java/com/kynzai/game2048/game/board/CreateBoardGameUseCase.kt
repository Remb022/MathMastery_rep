package com.kynzai.game2048.game.board

import com.kynzai.game2048.datastore.DEFAULT_VALUE
import javax.inject.Inject

class CreateBoardGameUseCase @Inject constructor(
    private val addNumberUseCase: AddNumberToBoardGameUseCase
) {
    fun createBoardGame(size: Int): MutableList<MutableList<Int>> {
        val boardGame = MutableList(size) { MutableList(size) { DEFAULT_VALUE } }

        return addNumberUseCase.addNumber(addNumberUseCase.addNumber(boardGame))
    }
}