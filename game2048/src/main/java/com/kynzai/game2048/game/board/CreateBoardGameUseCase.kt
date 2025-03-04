package com.kynzai.game2048.game.board

import com.kynzai.game2048.datastore.DEFAULT_VALUE
import javax.inject.Inject

class CreateBoardGameUseCase @Inject constructor(addNumberUseCase: AddNumberToBoardGameUseCase) {


    fun  createBoardGame (size: Int ) : MutableList<MutableList< Int >> {
        var boardGame = MutableList(size) {
            MutableList(size) {
                DEFAULT_VALUE
            }
        }

        val useCase = AddNumberToBoardGameUseCase()

        boardGame = useCase.addNumber(boardGame)
        boardGame = useCase.addNumber(boardGame)

        return boardGame
    }
}