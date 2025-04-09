package com.kynzai.game2048.di

import android.content.Context
import com.kynzai.game2048.datastore.DataStoreManager
import com.kynzai.game2048.game.board.AddNumberToBoardGameUseCase
import com.kynzai.game2048.game.board.CheckPossibleMovesUseCase
import com.kynzai.game2048.game.board.CreateBoardGameUseCase
import com.kynzai.game2048.game.logic.CombineAndMoveNumbersUseCase
import com.kynzai.game2048.game.logic.HasWonTheGameUseCase
import com.kynzai.game2048.game.logic.MoveNumbersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager{
        return DataStoreManager(context)
    }
    @Provides
    fun provideCombineAndMoveNumberUseCase() =
        CombineAndMoveNumbersUseCase()

    @Provides
    fun provideAddNumberToBoardGameUseCase() =
        AddNumberToBoardGameUseCase()

    @Provides
    fun provideCreateBoardGameUseCase(
        addNumberUseCase: AddNumberToBoardGameUseCase
    ) =
        CreateBoardGameUseCase(addNumberUseCase)

    @Provides
    fun provideIsTherePossibleMovesUseCase() =
        CheckPossibleMovesUseCase()

    @Provides
    fun provideHasWonTheGameUseCase() =
        HasWonTheGameUseCase()

    @Provides
    fun provideMoveNumbersUseCase(
        useCase1: CombineAndMoveNumbersUseCase,
        useCase2: AddNumberToBoardGameUseCase,
        useCase3: CheckPossibleMovesUseCase,
        useCase4: HasWonTheGameUseCase
    ): MoveNumbersUseCase {
        return MoveNumbersUseCase(useCase1, useCase2, useCase3, useCase4)
    }
}
