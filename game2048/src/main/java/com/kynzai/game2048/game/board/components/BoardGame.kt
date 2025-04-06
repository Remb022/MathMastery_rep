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
    currentBoard: List<List<Int>>,
    uiBoardSize: Dp
) {
    // Класс для хранения состояния плитки с историей перемещений
    data class Tile(
        val id: UUID,
        val value: Int,
        var currentRow: Int,
        var currentCol: Int,
        var previousRow: Int = currentRow,
        var previousCol: Int = currentCol
    )

    val cellSpacing = 8.dp
    val containerSize = uiBoardSize - cellSpacing * 2
    val tileSize = (containerSize - cellSpacing * 3) / 4

    // Состояние всех плиток
    val tilesState = remember { mutableStateOf(emptyList<Tile>()) }

    // Эффект для обновления позиций
    LaunchedEffect(currentBoard) {
        val previousTiles = tilesState.value.associateBy { it.currentRow to it.currentCol }
        val newTiles = mutableListOf<Tile>()

        // Сопоставляем новые позиции
        currentBoard.forEachIndexed { row, rows ->
            rows.forEachIndexed { col, value ->
                if (value != DEFAULT_VALUE) {
                    // Ищем плитку в предыдущем состоянии
                    val existingTile = previousTiles.values.firstOrNull {
                        it.value == value && !newTiles.any { t -> t.id == it.id }
                    }

                    if (existingTile != null) {
                        // Обновляем позицию существующей плитки
                        existingTile.previousRow = existingTile.currentRow
                        existingTile.previousCol = existingTile.currentCol
                        existingTile.currentRow = row
                        existingTile.currentCol = col
                        newTiles.add(existingTile)
                    } else {
                        // Создаем новую плитку
                        newTiles.add(
                            Tile(
                                id = UUID.randomUUID(),
                                value = value,
                                currentRow = row,
                                currentCol = col
                            )
                        )
                    }
                }
            }
        }

        tilesState.value = newTiles
    }

    Box(
        modifier = Modifier
            .size(uiBoardSize)
            .background(Grey2, RoundedCornerShape(corners))
            .padding(cellSpacing)
    ) {
        // Фоновые пустые клетки
        repeat(4) { row ->
            repeat(4) { col ->
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

        // Анимированные плитки
        tilesState.value.forEach { tile ->
            key(tile.id) {
                val targetX = (tileSize + cellSpacing) * tile.currentCol
                val targetY = (tileSize + cellSpacing) * tile.currentRow
                val startX = (tileSize + cellSpacing) * tile.previousCol
                val startY = (tileSize + cellSpacing) * tile.previousRow

                val offsetX = remember(tile.id) { Animatable(startX.value) }
                val offsetY = remember(tile.id) { Animatable(startY.value) }

                LaunchedEffect(tile.currentRow, tile.currentCol) {
                    if (tile.previousRow != tile.currentRow || tile.previousCol != tile.currentCol) {
                        offsetX.animateTo(targetX.value, tween(500))
                        offsetY.animateTo(targetY.value, tween(500))
                    } else {
                        offsetX.snapTo(targetX.value)
                        offsetY.snapTo(targetY.value)
                    }
                }

                BoardGameCell(
                    cellNumber = tile.value,
                    tileSize = tileSize,
                    modifier = Modifier
                        .offset(offsetX.value.dp, offsetY.value.dp)
                )
            }
        }
    }
}