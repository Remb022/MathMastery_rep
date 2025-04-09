package com.kynzai.game2048.game.logic

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

const val DragGesturesTag = "DragGestures"
@Composable
fun DragGesturesDirectionDetector(
    modifier: Modifier = Modifier,
    resetAtTheEnd: Boolean = true,
    difference: Float = 40f,
    onDirectionDetected: (MovementDirection) -> Unit,
    content: @Composable () -> Unit
) {

    var startPosition by remember { mutableStateOf(Offset(0f, 0f)) }
    var currentDirection = MovementDirection.NONE

    Box(modifier = modifier.pointerInput(Unit) {
        detectDragGestures(
            { offset ->
                Log.d(DragGesturesTag, "onDragStart: $currentDirection")
                startPosition = offset
            },
            {
                Log.d(DragGesturesTag, "onDragEnd: $currentDirection")
                if (!resetAtTheEnd) return@detectDragGestures

                startPosition = Offset(0f, 0f)
                currentDirection = MovementDirection.NONE
                onDirectionDetected(currentDirection)
            },
            {
                Log.d(DragGesturesTag, "onDragCancel")
                if (!resetAtTheEnd) return@detectDragGestures

                startPosition = Offset(0f, 0f)
                currentDirection = MovementDirection.NONE
                onDirectionDetected(currentDirection)
            },
        ) { change, dragAmount ->

            Log.d(DragGesturesTag, "onDrag -> change: $change, dragAmount: $dragAmount")
            if (currentDirection != MovementDirection.NONE) return@detectDragGestures

            val currentPosition = change.position

            val newDirection = getNewDirection(currentPosition, startPosition, difference)
            if (newDirection == MovementDirection.NONE) return@detectDragGestures

            currentDirection = newDirection
            onDirectionDetected(currentDirection)
        }
    }) {
        content()
    }
}
