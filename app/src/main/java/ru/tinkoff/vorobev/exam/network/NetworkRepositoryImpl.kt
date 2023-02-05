package ru.tinkoff.vorobev.exam.network

import ru.tinkoff.vorobev.exam.data.Film
import ru.tinkoff.vorobev.exam.data.FilmItem

class NetworkRepositoryImpl (private val serverApi: ServerApi, private val apiKey: String): NetworkRepository {
    override suspend fun getFilmItems(page: Int): ResultWrapper<List<FilmItem>> =
        try {
            ResultWrapper.Success(serverApi.getTopFilms(token = apiKey, page = page).transform())
        }
        catch (error: Throwable){
            println(error.message)
            ResultWrapper.NetworkError
        }

    override suspend fun getFilmInfoById(id: Int): ResultWrapper<Film> =
        try {
            ResultWrapper.Success(serverApi.getFilmInfo(token = apiKey, id = id).transform())
        }
        catch (error: Throwable){
            println(error.message)
            ResultWrapper.NetworkError
        }
}