package com.kynzai.game2048.game.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun GameOverDialog(score: Int, highScore: Int, onRestartClick: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Game Over",
                fontSize = 30.sp,
                fontWeight = FontWeight.Companion.Bold
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.Companion.CenterHorizontally) {
                Text(text = "Счет: $score", fontSize = 24.sp)
                Text(text = "Рекорд: $highScore", fontSize = 24.sp)
            }
        },
        confirmButton = {
            Button(onClick = onRestartClick) {
                Text(text = "Начать заново")
            }
        },
    )
}