package com.bugiman.domain.usecase.history

import com.bugiman.domain.repository.history.HistoryRepository

class HistoryItemUpdateNoteUseCase(
    private val repository: HistoryRepository
) {

    suspend operator fun invoke(itemId: Long, newNote: String) {
        repository.updateItemNote(
            itemId = itemId,
            newNote = newNote
        )
    }

}