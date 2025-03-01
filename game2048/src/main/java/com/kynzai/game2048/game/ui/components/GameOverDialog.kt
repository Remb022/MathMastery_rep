package com.kynzai.game2048.game.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun GameOverDialog(
    score: Int,
    highScore: Int,
    onRestartClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = "Game Over",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Счет: $score", fontSize = 24.sp)
                Text(text = "Рекорд: $highScore", fontSize = 24.sp)
            }
        },
        confirmButton = {
            Button(onClick = {
                onRestartClick()
                onDismiss()
            }) {
                Text(text = "Начать заново")
            }
        },
        dismissButton = {
            Button(onClick = {
                val deepLinkUri = Uri.parse("app://mathmastery/home")
                val intent = Intent(Intent.ACTION_VIEW, deepLinkUri)
                context.startActivity(intent)
                (context as? android.app.Activity)?.finish()
            }) {
                Text(text = "Домой")
            }
        }

    )
}
