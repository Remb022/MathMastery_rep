package com.example.mathmastery_beta

import android.content.Context
import android.util.Log
import com.example.mathmastery_beta.handlers.HandlerDataSave
import com.kynzai.game2048.game.logic.records.UserProgressManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Game2048Module {
    private const val TAG = "Game2048Module"

    @Provides
    @Singleton
    fun provideHandlerDataSave(@ApplicationContext context: Context): HandlerDataSave {
        Log.d(TAG, "Создание экземпляра HandlerDataSave...")
        return HandlerDataSave(context).also {
            Log.d(TAG, "HandlerDataSave создан: $it")
        }
    }

    @Provides
    @Singleton
    fun provideUserProgressManager(handlerDataSave: HandlerDataSave): UserProgressManager {
        Log.d(TAG, "Создание UserProgressManager с обработчиком: $handlerDataSave")
        return object : UserProgressManager {
            override fun addUserExp(expToSave: Int) {
                Log.d("EXP_TRANSFER", "🔼 [2048] Получено опыта: $expToSave")
                val beforeExp = handlerDataSave.getUserExp()
                val beforeLevel = handlerDataSave.getUserLevel()

                handlerDataSave.userLevelUp(expToSave)

                val afterExp = handlerDataSave.getUserExp()
                val afterLevel = handlerDataSave.getUserLevel()

                Log.d("EXP_TRANSFER", "🔽 [Handler] Результат: " +
                        "Уровень $beforeLevel→$afterLevel, " +
                        "Опыт $beforeExp→$afterExp")
            }
        }
    }

}
