package com.kynzai.game2048.game.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kynzai.game2048.R
import com.kynzai.game2048.game.ui.theme.Yallow

@Composable
fun HeaderPanel() {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .background(Yallow)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {

        Column {
            Text(
                "2048",
                color = Color.Companion.White,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 42.sp
            )
        }

        Spacer(modifier = Modifier.Companion.weight(1f))

        // Кнопка настроек
        IconButton(
            onClick = { /* Обработчик кнопки настроек */ },
            modifier = Modifier.Companion.size(70.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_homepage), // Ваш ресурс
                contentDescription = "homescreen",
                tint = Color.Companion.White,
                modifier = Modifier.Companion.size(40.dp)
            )
        }
    }
}