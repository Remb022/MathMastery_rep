package com.kynzai.game2048.game.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kynzai.game2048.R
import com.kynzai.game2048.game.ui.theme.Gray
import com.kynzai.game2048.game.ui.theme.Yallow

@Composable
fun HeaderPanel(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Yallow)
            .height(105.dp)
            .padding(25.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "2048",
                color = Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                val deepLinkUri = Uri.parse("app://mathmastery/home")
                val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
                (context as? android.app.Activity)?.finish()
            },
            modifier = Modifier.size(70.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_homepage),
                contentDescription = "homescreen",
                tint = Gray,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}
