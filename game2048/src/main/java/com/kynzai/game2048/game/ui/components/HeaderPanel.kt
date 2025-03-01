package com.kynzai.game2048.game.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kynzai.game2048.R
import com.kynzai.game2048.game.ui.theme.Yallow

@Composable
fun HeaderPanel() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Yallow)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "2048",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 42.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка настроек
        IconButton(
            onClick = {
                // Логика перехода на главный экран через deep link
                val deepLinkUri = Uri.parse("app://mathmastery/home")
                val intent = Intent(Intent.ACTION_VIEW, deepLinkUri)
                context.startActivity(intent)
                (context as? android.app.Activity)?.finish() // Закрываем текущий экран
            },
            modifier = Modifier.size(70.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_homepage),
                contentDescription = "homescreen",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}