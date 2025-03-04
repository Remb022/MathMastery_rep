package com.kynzai.game2048.game.board.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kynzai.game2048.datastore.DEFAULT_VALUE
import com.kynzai.game2048.game.logic.MovementDirection
import com.kynzai.game2048.game.ui.theme.getCellData
import com.kynzai.game2048.game.ui.corners
import com.kynzai.game2048.game.ui.theme.Grey2

@Composable
fun BoardGame(
    tableData: List<List<Int>>,
    currentDirection: MovementDirection,
    uiBoardSize: Dp
) {
    var previousBoard by remember { mutableStateOf(emptyList<List<Int>>()) }
    val cellSpacing = 8.dp
    val containerSize = uiBoardSize - cellSpacing * 2
    val tileSize = (containerSize - cellSpacing * 3) / 4

    Box(
        modifier = Modifier
            .size(uiBoardSize)
            .background(Grey2, RoundedCornerShape(corners))
            .padding(cellSpacing)
    ) {
        // Отображение пустых ячеек
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                EmptyCell(
                    tileSize = tileSize,
                    modifier = Modifier
                        .offset(
                            x = (tileSize + cellSpacing) * col,
                            y = (tileSize + cellSpacing) * row
                        )
                )
            }
        }

        // Отображение ячеек с числами
        tableData.forEachIndexed { row, rows ->
            rows.forEachIndexed { col, value ->
                if (value != DEFAULT_VALUE) {
                    key("$row-$col-$value") {
                        val offsetX by animateDpAsState(
                            targetValue = (tileSize + cellSpacing) * col,
                            animationSpec = tween(300)
                        )

                        val offsetY by animateDpAsState(
                            targetValue = (tileSize + cellSpacing) * row,
                            animationSpec = tween(300)
                        )

                        val scale = remember { Animatable(0.8f) }
                        LaunchedEffect(value) {
                            if (previousBoard.getOrNull(row)?.getOrNull(col) != value) {
                                scale.animateTo(1.2f, tween(100))
                                scale.animateTo(1f, tween(200))
                            }
                        }

                        BoardGameCell(
                            cellNumber = value,
                            tileSize = tileSize,
                            modifier = Modifier
                                .offset(offsetX, offsetY)
                                .graphicsLayer {
                                    scaleX = scale.value
                                    scaleY = scale.value
                                }
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(tableData) {
        previousBoard = tableData
    }
}

@Composable
fun BoardGameCell(
    cellNumber: Int,
    tileSize: Dp,
    modifier: Modifier = Modifier
) {
    val cellData = getCellData(cellNumber)
    val backgroundColor = cellData.backgroundColor
    val textColor = if (cellNumber < 8) Color.DarkGray else Color.White

    Box(
        modifier = modifier
            .size(tileSize)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(corners / 2))
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = cellNumber.toString(),
            color = textColor,
            fontSize = when {
                cellNumber > 512 -> 18.sp
                cellNumber > 64 -> 22.sp
                else -> 26.sp
            }
        )
    }
}

@Composable
fun EmptyCell(
    tileSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(tileSize)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(corners / 2)
            )
    )
}
