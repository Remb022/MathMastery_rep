package com.kynzai.game2048.game.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.kynzai.game2048.R
import com.kynzai.game2048.datastore.GameState
import com.kynzai.game2048.game.board.GameStatus
import com.kynzai.game2048.game.logic.DragGesturesDirectionDetector
import com.kynzai.game2048.game.logic.MovementDirection
import com.kynzai.game2048.game.board.components.BoardGame
import com.kynzai.game2048.game.ui.components.AppNameDefaultHeight
import com.kynzai.game2048.game.ui.components.GameOverDialog
import com.kynzai.game2048.game.ui.components.HeaderPanel
import com.kynzai.game2048.game.ui.components.HighScoreDisplay
import com.kynzai.game2048.game.ui.components.IconButton
import com.kynzai.game2048.game.ui.components.IconButtonHeight
import com.kynzai.game2048.game.ui.theme.White2
import com.kynzai.game2048.game.viewmodel.TwoZeroFourEightViewModel


val corners = 10.dp
val paddings_inside_board = 3.dp
val paddings_outside_board = 18.dp
val SpaceBeforeBoard = 50.dp

@Composable
fun BoardGameScreen(viewModel: TwoZeroFourEightViewModel, uiState: GameState) {
    var currentDirection by remember { mutableStateOf(MovementDirection.NONE) }
    var showGameOverDialog by remember { mutableStateOf(false) } // Состояние диалога

    val configuration = LocalConfiguration.current
    val uiBoardSize = configuration.screenWidthDp.dp.minus(paddings_outside_board + paddings_outside_board)
    val reStartButtonY = paddings_outside_board + AppNameDefaultHeight + SpaceBeforeBoard + uiBoardSize + 10.dp

    LaunchedEffect(uiState.gameStatus) {
        if (uiState.gameStatus == GameStatus.GAME_OVER) {
            showGameOverDialog = true
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White2),
            horizontalAlignment = CenterHorizontally
        ) {
            HeaderPanel()

            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = paddings_outside_board,
                        end = paddings_outside_board,
                        top = paddings_outside_board,
                        bottom = SpaceBeforeBoard
                    ),
                horizontalArrangement = Arrangement.Start
            ) {
                HighScoreDisplay(uiState)
            }

            BoardGame(uiState.board, currentDirection, uiBoardSize)

            Spacer(modifier = Modifier.Companion.height(IconButtonHeight + 10.dp))

            if (showGameOverDialog) {
                GameOverDialog(
                    score = uiState.currentScore,
                    highScore = uiState.highScore,
                    onRestartClick = {
                        viewModel.startNewGame()
                        showGameOverDialog = false
                    },
                    onDismiss = {
                        showGameOverDialog = false
                    }
                )
            }
        }

        DragGesturesDirectionDetector(
            modifier = Modifier.fillMaxSize(),
            onDirectionDetected = {
                currentDirection = it
                viewModel.moveNumbers(it)
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 180.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier.offset(y = reStartButtonY),
                    iconResource = R.drawable.start_again,
                    onClick = { viewModel.startNewGame() }
                )
            }
        }
    }
}



