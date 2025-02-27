package com.kynzai.game2048.game.ui.theme

import androidx.compose.ui.graphics.Color
import com.kynzai.game2048.datastore.DEFAULT_VALUE

fun getCellData(cellData: Int): CellData {
    val backgroundColor = when (cellData) {
        DEFAULT_VALUE -> Grey1
        2 -> Yellow7
        4 -> Yellow6
        8 -> Yellow5
        16 -> Yellow4
        32 -> Yellow3
        64 -> Yellow2
        128 -> Yellow1

        256 -> Yellow7
        512 -> Yellow6
        1024 -> Yellow5
        2048 -> Yellow4
        4096 -> Yellow3
        8192 -> Yellow2

        else -> Yellow7
    }
    val textColor = when (cellData) {
        2, 4, 8, 16 -> Black
        32, 64, 128 -> White

        256, 512, 1024, 2048 -> Black
        4096, 8192 -> White

        else -> Black
    }
    return CellData(
        backgroundColor = backgroundColor,
        textColor = textColor
    )
}
data class CellData(
    val backgroundColor: Color,
    val textColor: Color
)
