package com.kynzai.game2048.game.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kynzai.game2048.R
import com.kynzai.game2048.game.ui.theme.Gray
import com.kynzai.game2048.game.ui.theme.White

@Composable
fun GameOverDialog(
    score: Int,
    highScore: Int,
    onRestartClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            modifier = Modifier
                .widthIn(min = 500.dp, max = 900.dp)
                .background(
                    White,
                    shape = RoundedCornerShape(15.dp)),
            shape = RoundedCornerShape(15.dp),
            color = White
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = "Игра окончена",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Счет: $score",
                    fontSize = 30.sp,
                    color = Gray
                )
                Text(
                    text = "Рекорд: $highScore",
                    fontSize = 30.sp,
                    color = Gray
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp), // Отступы по бокам
                    horizontalArrangement = Arrangement.SpaceBetween, // Разделяем иконки по бокам
                ) {
                    Button(
                        onClick = {
                            onRestartClick()
                            onDismiss()
                        },
                        modifier = Modifier
                            .padding(top = 10.dp, start = 0.dp)
                            .size(40.dp), // Убираем лишние отступы и даем размер кнопке
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_retry),
                            contentDescription = "Replay",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )
                    }
                    Button(
                        onClick = {
                            val deepLinkUri = Uri.parse("app://mathmastery/home")
                            val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(intent)
                            (context as? android.app.Activity)?.finish()
                        },
                        modifier = Modifier
                            .padding(top = 10.dp, end = 0.dp)
                            .size(40.dp), // Убираем лишние отступы и даем размер кнопке
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_confirm),
                            contentDescription = "Home",
                            modifier = Modifier.size(30.dp),
                            tint = Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(top = 10.dp))
            }
        }
    }
}