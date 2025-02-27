package com.kynzai.game2048.game.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.kynzai.game2048.datastore.GameState

@Composable
fun HighScoreDisplay(uiState: GameState) {
    println(" [HighScoreDisplay] Current score: ${uiState.currentScore}, High Score: ${uiState.highScore}")
    Text(
        text = "Счет: ${uiState.currentScore} \nРекорд: ${uiState.highScore}",
        fontSize = 24.sp
    )
}