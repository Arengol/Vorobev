package ru.tinkoff.vorobev.exam.viewmodel

import androidx.lifecycle.LiveData
import ru.tinkoff.vorobev.exam.data.Film
import ru.tinkoff.vorobev.exam.data.FilmItem

interface FilmViewModel {
    fun getFilms()
    fun getFilmInfo()
    fun searchFilm(searchRequest: String)
    fun normalizeState()
    val searchFilmData: LiveData<List<FilmItem>>
    val popularFilmsState: LiveData<UiState>
    val popularFilmsData: LiveData<List<FilmItem>>
    val filmState: LiveData<UiState>
    val filmData: LiveData<Film>
    var selectedFilm: Int
}