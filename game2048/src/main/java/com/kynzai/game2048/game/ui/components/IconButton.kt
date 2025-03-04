package com.kynzai.game2048.game.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kynzai.game2048.R
import com.kynzai.game2048.game.board.GameStatus
import com.kynzai.game2048.game.ui.corners
import com.kynzai.game2048.game.ui.theme.Yellow1
import com.kynzai.game2048.game.ui.theme.Yellow3

val AppNameDefaultHeight = 60. dp
val IconButtonHeight = 70. dp

@Composable
fun RestartButton(
    modifier: Modifier = Modifier,
    gameStatus: GameStatus,
    onRestartConfirmed: () -> Unit,
    @DrawableRes iconResource: Int = R.drawable.start_again // Иконка по умолчанию
) {
    var showGameOverDialog by remember { mutableStateOf(false) }

    // Диалог подтверждения
    if (showGameOverDialog) {
        AlertDialog(
            onDismissRequest = { showGameOverDialog = false },
            title = { Text("Перезапустить игру?") },
            text = { Text("Текущий прогресс будет потерян.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRestartConfirmed()
                        showGameOverDialog = false
                    }
                ) { Text("Да") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showGameOverDialog = false }
                ) { Text("Нет") }
            }
        )
    }

    // Кнопка с новым дизайном
    Button(
        onClick = {
            if (gameStatus == GameStatus.PLAYING) {
                showGameOverDialog = true
            } else {
                onRestartConfirmed()
            }
        },
        modifier = modifier
            .height(IconButtonHeight)
            .width(IconButtonHeight)
            .border(
                shape = RoundedCornerShape(corners),
                color = Color.Black,
                width = 1.dp
            ),
        shape = RoundedCornerShape(corners),
        colors = ButtonDefaults.buttonColors(
            containerColor = Yellow3,
            contentColor = Color.Black,
            disabledContainerColor = Yellow1,
            disabledContentColor = Color.White
        )
    ) {
        Image(
            painter = painterResource(iconResource),
            contentDescription = "Начать заново",
            modifier = Modifier.size(150.dp)
        )
    }
}