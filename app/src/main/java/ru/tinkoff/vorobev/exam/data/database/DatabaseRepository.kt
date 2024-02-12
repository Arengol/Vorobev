package ru.tinkoff.vorobev.exam.data.database

import ru.tinkoff.vorobev.exam.data.dto.Film
import ru.tinkoff.vorobev.exam.data.dto.FilmItem

interface DatabaseRepository {
    suspend fun getAllFavoriteFilm(): List<FilmItem>
    suspend fun getFavoriteFilmIfo(filmItem: FilmItem): Film
    suspend fun insertFavoriteFilm(filmItem: FilmItem, film: Film)
    suspend fun deleteFavoriteFilm(filmItem: FilmItem)

}