package com.kynzai.game2048.game.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter.Companion.tint

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kynzai.game2048.game.ui.theme.Gray
import com.kynzai.game2048.game.ui.theme.White
import com.kynzai.game2048.game.ui.theme.Yallow
import com.kynzai.game2048.game.ui.theme.Yellow_gray

val AppNameDefaultHeight = 60. dp
val IconButtonHeight = 70. dp

@Composable
fun RestartButton(
    modifier: Modifier = Modifier,
    gameStatus: GameStatus,
    onRestartConfirmed: () -> Unit,
    @DrawableRes iconResource: Int = R.drawable.start_again
) {
    var showRestartDialog by remember { mutableStateOf(false) }

    if (showRestartDialog) {
        val context = LocalContext.current

        Dialog(
            onDismissRequest = { showRestartDialog = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        ) {
            Surface(
                modifier = Modifier
                    .widthIn(min = 500.dp, max = 900.dp)
                    .background(White, shape = RoundedCornerShape(15.dp)),
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
                        text = "Перезапустить игру?",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Текущий прогресс будет потерян.",
                        fontSize = 20.sp,
                        color = Gray
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                onRestartConfirmed()
                                showRestartDialog = false
                            },
                            modifier = Modifier
                                .padding(top = 10.dp, start = 0.dp)
                                .size(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_retry),
                                contentDescription = "Restart",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Black
                            )
                        }
                        Button(
                            onClick = {
                                showRestartDialog = false
                            },
                            modifier = Modifier
                                .padding(top = 10.dp, end = 0.dp)
                                .size(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_confirm),
                                contentDescription = "Cancel",
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

    Button(
        onClick = {
            if (gameStatus == GameStatus.PLAYING) {
                showRestartDialog = true
            } else {
                onRestartConfirmed()
            }
        },
        modifier = modifier
            .height(IconButtonHeight)
            .width(IconButtonHeight)
            ,
        shape = RoundedCornerShape(corners),
        colors = ButtonDefaults.buttonColors(
            containerColor = Yallow,
            contentColor = Color.Black,
            disabledContainerColor = Yellow1,
            disabledContentColor = Color.White
        )
    ) {
        Image(
            painter = painterResource(iconResource),
            contentDescription = "Начать заново",
            colorFilter = tint(Gray),
            modifier = Modifier.size(40.dp)
        )
    }
}
