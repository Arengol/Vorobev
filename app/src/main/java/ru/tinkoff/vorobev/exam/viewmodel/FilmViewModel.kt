package ru.tinkoff.vorobev.exam.viewmodel

import androidx.lifecycle.LiveData
import ru.tinkoff.vorobev.exam.data.dto.Film
import ru.tinkoff.vorobev.exam.data.dto.FilmItem

interface FilmViewModel {
    fun getFilms()
    fun getFilmInfo()
    fun searchFilm(searchRequest: String)
    fun normalizeState()
    fun addFavoriteFilm(filmItem: FilmItem)
    fun deleteFavoriteFilm(filmItem: FilmItem)
    val searchFilmData: LiveData<List<FilmItem>>
    val popularFilmsState: LiveData<UiState>
    val popularFilmsData: LiveData<List<FilmItem>>
    val filmState: LiveData<UiState>
    val filmData: LiveData<Film>
    var selectedFilm: FilmItem
}