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
import kotlin.math.abs

@Composable
fun BoardGame(
    tableData: List<List<Int>>,
    uiBoardSize: Dp
) {
    data class AnimatableTile(
        val value: Int,
        val row: Int,
        val col: Int,
        val uniqueId: String = UUID.randomUUID().toString()
    )

    var previousBoard by remember { mutableStateOf(emptyList<List<Int>>()) }
    var previousNumberPositions by remember { mutableStateOf(emptyMap<Int, List<Pair<Int, Int>>>()) }

    val cellSpacing = 8.dp
    val containerSize = uiBoardSize - cellSpacing * 2
    val tileSize = (containerSize - cellSpacing * 3) / 4

    val animatableTiles = remember(tableData) {
        tableData.mapIndexed { row, rows ->
            rows.mapIndexed { col, value ->
                AnimatableTile(value, row, col)
            }
        }
    }

    fun findPreviousPosition(
        value: Int,
        currentRow: Int,
        currentCol: Int
    ): Pair<Int, Int> {
        val previousPositions = previousNumberPositions.getOrDefault(value, emptyList())

        if (previousPositions.isNotEmpty()) {
            val closestPosition = previousPositions.minByOrNull { prevPos ->
                abs(prevPos.first - currentRow) + abs(prevPos.second - currentCol)
            }
            return closestPosition ?: Pair(currentRow, currentCol)
        }

        return Pair(currentRow, currentCol)
    }

    Box(
        modifier = Modifier
            .size(uiBoardSize)
            .background(Grey2, RoundedCornerShape(corners))
            .padding(cellSpacing)
    ) {
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

        animatableTiles.forEachIndexed { row, rows ->
            rows.forEachIndexed { col, animatableTile ->
                val value = animatableTile.value

                if (value != DEFAULT_VALUE) {
                    key(animatableTile.uniqueId) {
                        val previousPosition = findPreviousPosition(value, row, col)

                        val startX = (tileSize + cellSpacing) * previousPosition.second
                        val startY = (tileSize + cellSpacing) * previousPosition.first

                        val targetX = (tileSize + cellSpacing) * col
                        val targetY = (tileSize + cellSpacing) * row

                        val offsetX = remember { Animatable(startX.value) }
                        LaunchedEffect(targetX) {
                            if (startX != targetX) {
                                offsetX.animateTo(
                                    targetValue = targetX.value,
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = LinearEasing
                                    )
                                )
                            }
                        }

                        val offsetY = remember { Animatable(startY.value) }
                        LaunchedEffect(targetY) {
                            if (startY != targetY) {
                                offsetY.animateTo(
                                    targetValue = targetY.value,
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = LinearEasing
                                    )
                                )
                            }
                        }

                        val scale = remember { Animatable(1f) }
                        LaunchedEffect(value) {
                            if (previousBoard.getOrNull(row)?.getOrNull(col) != value) {
                                scale.animateTo(1.2f, tween(durationMillis = 100))
                                scale.animateTo(1f, tween(durationMillis = 100))
                            }
                        }

                        BoardGameCell(
                            cellNumber = value,
                            tileSize = tileSize,
                            modifier = Modifier
                                .offset(offsetX.value.dp, offsetY.value.dp)
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
        val newNumberPositions = mutableMapOf<Int, List<Pair<Int, Int>>>()

        tableData.forEachIndexed { row, rows ->
            rows.forEachIndexed { col, value ->
                if (value != DEFAULT_VALUE) {
                    val positions = newNumberPositions.getOrDefault(value, emptyList())
                    newNumberPositions[value] = positions + Pair(row, col)
                }
            }
        }

        previousNumberPositions = newNumberPositions
        previousBoard = tableData
    }
}
