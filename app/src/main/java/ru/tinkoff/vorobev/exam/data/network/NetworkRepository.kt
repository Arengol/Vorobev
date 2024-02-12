package ru.tinkoff.vorobev.exam.data.network

import ru.tinkoff.vorobev.exam.data.dto.Film
import ru.tinkoff.vorobev.exam.data.dto.FilmItem

interface NetworkRepository {
    suspend fun getFilmItems (): ResultWrapper<List<FilmItem>>
    suspend fun getFilmInfoById (id: Int): ResultWrapper<Film>
}