package com.bugiman.composercalculator.di

import com.bugiman.domain.repository.history.HistoryRepository
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryItemCopyExpressionUseCase
import com.bugiman.domain.usecase.history.HistoryItemCopyResultUseCase
import com.bugiman.domain.usecase.history.HistoryItemDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryItemEditUseCase
import com.bugiman.domain.usecase.history.HistoryItemUpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideHistoryAllGetUseCase(
        repository: HistoryRepository
    ): HistoryAllGetUseCase {
        return HistoryAllGetUseCase(repository)
    }

    @Provides
    fun provideHistoryAllDeleteUseCase(
        repository: HistoryRepository
    ): HistoryAllDeleteUseCase {
        return HistoryAllDeleteUseCase(repository)
    }

    @Provides
    fun provideHistoryItemCopyExpressionUseCase(
        repository: HistoryRepository
    ): HistoryItemCopyExpressionUseCase {
        return HistoryItemCopyExpressionUseCase(repository)
    }

    @Provides
    fun provideHistoryItemCopyResultUseCase(
        repository: HistoryRepository
    ): HistoryItemCopyResultUseCase {
        return HistoryItemCopyResultUseCase(repository)
    }

    @Provides
    fun provideHistoryItemDeleteUseCase(
        repository: HistoryRepository
    ): HistoryItemDeleteUseCase {
        return HistoryItemDeleteUseCase(repository)
    }

    @Provides
    fun provideHistoryItemEditUseCase(
        repository: HistoryRepository
    ): HistoryItemEditUseCase {
        return HistoryItemEditUseCase(repository)
    }

    @Provides
    fun provideHistoryItemUpdateNoteUseCase(
        repository: HistoryRepository
    ): HistoryItemUpdateNoteUseCase {
        return HistoryItemUpdateNoteUseCase(repository)
    }

}