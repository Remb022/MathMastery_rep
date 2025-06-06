package com.kynzai.game2048.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kynzai.game2048.R
import com.kynzai.game2048.datastore.GameState
import com.kynzai.game2048.game.board.GameStatus
import com.kynzai.game2048.game.board.components.BoardGame
import com.kynzai.game2048.game.logic.DragGesturesDirectionDetector
import com.kynzai.game2048.game.logic.MovementDirection
import com.kynzai.game2048.game.ui.components.AppNameDefaultHeight
import com.kynzai.game2048.game.ui.components.GameOverDialog
import com.kynzai.game2048.game.ui.components.HeaderPanel
import com.kynzai.game2048.game.ui.components.HighScoreDisplay
import com.kynzai.game2048.game.ui.components.IconButtonHeight
import com.kynzai.game2048.game.ui.components.RestartButton
import com.kynzai.game2048.game.ui.theme.White2
import com.kynzai.game2048.game.viewmodel.TwoZeroFourEightViewModel

val corners = 10.dp
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
        Row(
            modifier = Modifier.fillMaxWidth()
                .zIndex(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            HeaderPanel(
                modifier = Modifier.clickable {
                }
            )

        }

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxSize()
                .background(White2),
            horizontalAlignment = CenterHorizontally
        ) {

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

            BoardGame(uiState.board, uiBoardSize, )

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
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 180.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                RestartButton(
                    modifier = Modifier
                        .offset(y = reStartButtonY)
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(15.dp) ,
                            clip = false
                        ),
                    gameStatus = uiState.gameStatus,
                    onRestartConfirmed = { viewModel.startNewGame() },
                    iconResource = R.drawable.start_again // Иконка для кнопки
                )
            }
        }
    }
}