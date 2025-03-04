package com.kynzai.game2048.game.logic

import androidx.compose.ui.geometry.Offset


fun getNewDirection(
    currentPosition: Offset,
    startPosition: Offset,
    difference: Float,
): MovementDirection {
    val deltaX = currentPosition.x - startPosition.x
    val deltaY = currentPosition.y - startPosition.y

    return when {
        deltaX > difference -> MovementDirection.RIGHT
        deltaX < -difference -> MovementDirection.LEFT
        deltaY > difference -> MovementDirection.DOWN
        deltaY < -difference -> MovementDirection.UP
        else -> MovementDirection.NONE
    }
}