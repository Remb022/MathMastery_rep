package com.kynzai.game2048.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.kynzai.game2048.game.board.GameStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class DataStoreManager @Inject constructor(private val context: Context) {

    companion object {
        val HIGH_SCORE_KEY = intPreferencesKey("high_score")
        val CURRENT_SCORE_KEY = intPreferencesKey("current_score")
        val BOARD_STATE_KEY = stringPreferencesKey("board_state")
        val GAME_STATUS_KEY = intPreferencesKey("game_status")
    }

    val highScoreFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[HIGH_SCORE_KEY] ?: 0
    }

    suspend fun saveHighScore(newHighScore: Int) {
        context.dataStore.edit { preferences ->
            val currentHighScore = preferences[HIGH_SCORE_KEY] ?: 0
            if (newHighScore > currentHighScore) {
                preferences[HIGH_SCORE_KEY] = newHighScore
            }
        }
    }

    suspend fun saveGameState(board: List<List<Int>>, currentScore: Int, gameStatus: Int) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_SCORE_KEY] = currentScore
            preferences[GAME_STATUS_KEY] = gameStatus

            // Преобразуем board в строку для хранения
            val boardString = board.joinToString(separator = ";") { row -> row.joinToString(",") }
            preferences[BOARD_STATE_KEY] = boardString
        }
    }

    val gameStateFlow: Flow<GameState> = context.dataStore.data.map { preferences ->
        val currentScore = preferences[CURRENT_SCORE_KEY] ?: 0
        val highScore = preferences[HIGH_SCORE_KEY] ?: 0
        val gameStatus = preferences[GAME_STATUS_KEY] ?: GameStatus.PLAYING.ordinal

        val boardString = preferences[BOARD_STATE_KEY] ?: ""
        val board = if (boardString.isNotEmpty()) {
            boardString.split(";").map { row -> row.split(",").map { it.toInt() }.toMutableList() }.toMutableList()
        } else {
            List(4) { MutableList(4) { 0 } }.toMutableList() // Пустая доска, если данных нет
        }

        GameState(
            board = board,
            currentScore = currentScore,
            highScore = highScore,
            gameStatus = GameStatus.values()[gameStatus]
        )
    }
}
