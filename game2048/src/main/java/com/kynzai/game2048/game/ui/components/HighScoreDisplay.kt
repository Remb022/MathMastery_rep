package com.kynzai.game2048.game.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kynzai.game2048.datastore.GameState
import com.kynzai.game2048.game.ui.theme.Yellow_gray

@Composable
fun HighScoreDisplay(uiState: GameState) {
    Column {
        Text(
            text = "Счет: ${uiState.currentScore} ",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Yellow_gray,
            modifier = Modifier.padding(top = 10.dp)

        )
        Text(
            text = "Рекорд: ${uiState.highScore} ",
            fontSize = 20.sp,
            color = Yellow_gray,
            fontWeight = FontWeight.Bold
        )
    }
}