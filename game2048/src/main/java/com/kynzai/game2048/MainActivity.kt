package com.kynzai.game2048

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Game2048Screen(onBackPressed = { onBackPressedDispatcher.onBackPressed() })
        }
    }
}

@Composable
fun Game2048Screen(onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Black, Color.Magenta)
    val text = "Вы лошки на 100%"

    val annotatedString = buildAnnotatedString {
        text.forEachIndexed { index, char ->
            withStyle(style = SpanStyle(color = colors[index % colors.size], fontWeight = FontWeight.Bold)) {
                append(char)
            }
        }
    }

    val openDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = annotatedString,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )

        Button(
            onClick = { openDialog.value = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Назад")
        }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = { Text(text = "Подтверди что ты лох чтобы вернуться назад") },
                text = { Text(text = "Уверен что лох?") },
                confirmButton = {
                    Button(onClick = {
                        openDialog.value = false
                        showToast2(context)

                        onBackPressed()
                    }) {
                        Text("Да")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        openDialog.value = false
                        showToast(context)
                    }) {
                        Text("Нет")
                    }
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    }
}

fun showToast(context: android.content.Context) {
    Toast.makeText(context, "Терпила", Toast.LENGTH_SHORT).show()
}
fun showToast2(context: android.content.Context) {
    Toast.makeText(context, "Я знал это", Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun Game2048ScreenPreview() {
    Game2048Screen(onBackPressed = {})
}
