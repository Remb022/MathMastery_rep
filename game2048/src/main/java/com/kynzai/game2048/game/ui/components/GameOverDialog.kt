package com.kynzai.game2048.game.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kynzai.game2048.game.ui.theme.Gray
import com.kynzai.game2048.game.ui.theme.LightYallow
import com.kynzai.game2048.game.ui.theme.White2
import com.kynzai.game2048.game.ui.theme.Yallow
import com.kynzai.game2048.game.ui.theme.Yellow1

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
                .widthIn(min = 300.dp, max = 400.dp)
                .background(
                    LightYallow,
                    shape = RoundedCornerShape(15.dp)),
            shape = RoundedCornerShape(15.dp),
            color = LightYallow
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Over",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Счет: $score",
                    fontSize = 25.sp,
                    color = Gray
                )
                Text(
                    text = "Рекорд: $highScore",
                    fontSize = 25.sp,
                    color = Gray
                )
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            onRestartClick()
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Yallow)
                    ) {
                        Text(
                            text = "Начать заново",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gray
                        )
                    }
                    Button(
                        onClick = {
                            //TODO: Оставить только вызов
                            val deepLinkUri = Uri.parse("app://mathmastery/home")
                            val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(intent)
                            (context as? android.app.Activity)?.finish()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Yallow)
                    ) {
                        Text(
                            text = "Домой",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gray
                        )
                    }
                }
            }
        }
    }
}