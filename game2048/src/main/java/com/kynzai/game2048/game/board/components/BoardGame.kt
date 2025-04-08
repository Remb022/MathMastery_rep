package com.kynzai.game2048.game.board.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kynzai.game2048.datastore.DEFAULT_VALUE
import com.kynzai.game2048.game.logic.MovementDirection
import com.kynzai.game2048.game.ui.corners
import com.kynzai.game2048.game.ui.theme.Grey2
import java.util.UUID

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoardGame(
    currentBoard: List<List<Int>>,
    uiBoardSize: Dp
) {
    val cellSpacing = 8.dp
    val boardSize = currentBoard.size
    if (boardSize == 0 || currentBoard[0].isEmpty()) return

    val containerSize = uiBoardSize - cellSpacing * 2
    val tileSize = (containerSize - cellSpacing * (boardSize - 1)) / boardSize

    Box(
        modifier = Modifier
            .size(uiBoardSize)
            .background(Grey2, RoundedCornerShape(corners))
            .padding(cellSpacing)
    ) {
        // Отрисовка пустых клеток (фон)
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                EmptyCell(
                    tileSize = tileSize,
                    modifier = Modifier.offset(
                        x = (tileSize + cellSpacing) * col,
                        y = (tileSize + cellSpacing) * row
                    )
                )
            }
        }

        // Отрисовка плиток
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                if (currentBoard[row][col] != DEFAULT_VALUE) {
                    BoardGameCell(
                        cellNumber = currentBoard[row][col],
                        tileSize = tileSize,
                        modifier = Modifier.offset(
                            x = (tileSize + cellSpacing) * col,
                            y = (tileSize + cellSpacing) * row
                        )
                    )
                }
            }
        }
    }
}