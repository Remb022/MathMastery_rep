package com.kynzai.game2048.game.board

const val DEFAULT_NUMBER_TO_WIN = 2048

data class MoveNumberResult(
    var boardGame: MutableList<MutableList< Int >>,
    var gameStatus: GameStatus = GameStatus.PLAYING,
    var numberToWin: Int,
    val currentScore: Int = 0,
    val score: Int ,
)

