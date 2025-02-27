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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kynzai.game2048.game.ui.corners
import com.kynzai.game2048.game.ui.theme.Yellow1
import com.kynzai.game2048.game.ui.theme.Yellow3

val AppNameDefaultHeight = 60. dp
val IconButtonHeight = 70. dp

@Composable
fun  IconButton (
    onClick: () -> Unit,
    @DrawableRes iconResource: Int,

    modifier: Modifier = Modifier.Companion
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(IconButtonHeight)
            .width(IconButtonHeight)
            .border(
                shape = RoundedCornerShape(corners),
                color = Color.Companion.Black,
                width = 1.dp
            ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(corners),
        colors = ButtonDefaults.buttonColors(
            containerColor = Yellow3,
            contentColor = Color.Companion.Black,
            disabledContainerColor = Yellow1,
            disabledContentColor = Color.Companion.White
        )
    ) {
        Image(
            painter = painterResource(iconResource),
            contentDescription = null,
            modifier = Modifier.Companion
                .size(150.dp),
        )
    }
}