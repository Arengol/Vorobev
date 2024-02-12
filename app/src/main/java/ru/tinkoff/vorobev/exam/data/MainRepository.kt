package ru.tinkoff.vorobev.exam.data

import ru.tinkoff.vorobev.exam.data.dto.Film
import ru.tinkoff.vorobev.exam.data.dto.FilmItem

interface MainRepository {
    suspend fun getAllFilms(): List<FilmItem>
    suspend fun getAllFavoriteFilms(): List<FilmItem>
    suspend fun getFilmInfo(filmItem: FilmItem): Film
    suspend fun addFavoriteFilm(filmItem: FilmItem)
    suspend fun deleteFavoriteFilm(filmItem: FilmItem)
}