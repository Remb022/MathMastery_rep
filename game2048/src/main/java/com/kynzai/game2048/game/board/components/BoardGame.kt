package com.kynzai.game2048.game.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kynzai.game2048.datastore.DEFAULT_VALUE
import com.kynzai.game2048.game.logic.MovementDirection
import com.kynzai.game2048.game.ui.theme.getCellData
import com.kynzai.game2048.game.ui.corners
import com.kynzai.game2048.game.ui.paddings_inside_board
import com.kynzai.game2048.game.ui.theme.Grey2

@Composable
fun BoardGame(tableData: List<List<Int>>, currentDirection: MovementDirection, uiBoardSize: Dp) {
    LazyColumn(
        modifier = Modifier.Companion
            .width(uiBoardSize)
            .height(uiBoardSize)
            .background(
                color = Grey2,
                shape = RoundedCornerShape(corners)
            )
            .padding(paddings_inside_board)
    ) {
        items(
            items = tableData,
        ) { row ->
            BoardGameRow(rowData = row, uiBoardSize - paddings_inside_board - paddings_inside_board)
        }
    }
}

@Composable
fun BoardGameRow(rowData: List<Int>, uiBoardSize: Dp) {
    val uiCellSize = uiBoardSize.div(rowData.size)

    LazyRow(
        modifier = Modifier
            .width(uiBoardSize)
            .height(uiCellSize)
    ) {
        items(
            items = rowData,
        ) { cellData ->
            BoardGameCell(cellData, uiCellSize)
        }
    }
}

@Composable
fun BoardGameCell(cellNumber: Int, uiCellSize: Dp) {
    val cellData = getCellData(cellNumber)
    Box(modifier = Modifier
        .width(uiCellSize)
        .height(uiCellSize)
        .padding(paddings_inside_board)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(corners),
                    color = cellData.backgroundColor
                )
                .border(
                    shape = RoundedCornerShape(corners),
                    color = Black,
                    width = 1.dp
                ),
            contentAlignment = Center
        ) {
            Text(
                fontSize = 25.sp,
                color = cellData.textColor,
                fontWeight = FontWeight.Bold,
                text = if (cellNumber == DEFAULT_VALUE) "" else cellNumber.toString()
            )
        }
    }
}
