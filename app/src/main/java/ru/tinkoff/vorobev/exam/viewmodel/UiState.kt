package ru.tinkoff.vorobev.exam.viewmodel

sealed interface UiState {
    object Wait: UiState
    object Loading: UiState
    object Success: UiState
    object Error: UiState
}