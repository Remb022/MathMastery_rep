package com.kynzai.game2048.game.ui

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kynzai.game2048.game.viewmodel.TwoZeroFourEightViewModel


@Composable
fun TwoZeroFourEightApp() {

    val viewModel: TwoZeroFourEightViewModel = viewModel()
    val uiState by viewModel.gameState.collectAsState()

    BoardGameScreen(viewModel, uiState)
}

