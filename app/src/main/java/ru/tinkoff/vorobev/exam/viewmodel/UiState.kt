package ru.tinkoff.vorobev.exam.viewmodel

interface UiState {
    object Wait: UiState
    object Loading: UiState
    object Success: UiState
    object Error: UiState
    object SearchError: UiState
    object SearchSuccess: UiState
}