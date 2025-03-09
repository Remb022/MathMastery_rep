package com.kynzai.game2048.game.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kynzai.game2048.game.ui.corners
import com.kynzai.game2048.game.ui.theme.getCellData

@Composable
fun BoardGameCell(
    cellNumber: Int,
    tileSize: Dp,
    modifier: Modifier = Modifier.Companion
) {
    val cellData = getCellData(cellNumber)
    val backgroundColor = cellData.backgroundColor
    val textColor = if (cellNumber < 8) Color.Companion.DarkGray else Color.Companion.White

    Box(
        modifier = modifier
            .size(tileSize)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(corners / 2)
            )
            .padding(4.dp),
        contentAlignment = Alignment.Companion.Center
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