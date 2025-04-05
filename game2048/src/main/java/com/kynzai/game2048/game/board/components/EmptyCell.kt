package com.kynzai.game2048.game.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.kynzai.game2048.game.ui.corners

@Composable
fun EmptyCell(
    tileSize: Dp,
    modifier: Modifier = Modifier.Companion
) {
    Box(
        modifier = modifier
            .size(tileSize)
            .background(
                color = Color.Companion.LightGray,
                shape = RoundedCornerShape(corners / 2)
            )
    )
}