package ru.tinkoff.vorobev.exam.network

import ru.tinkoff.vorobev.exam.data.Film
import ru.tinkoff.vorobev.exam.data.FilmItem

interface NetworkRepository {
    suspend fun getFilmItems (page: Int): ResultWrapper<List<FilmItem>>
    suspend fun getFilmInfoById (id: Int): ResultWrapper<Film>
}