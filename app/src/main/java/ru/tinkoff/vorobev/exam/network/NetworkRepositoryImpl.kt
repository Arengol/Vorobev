package ru.tinkoff.vorobev.exam.network

import android.annotation.SuppressLint
import ru.tinkoff.vorobev.exam.data.Film
import ru.tinkoff.vorobev.exam.data.FilmItem

class NetworkRepositoryImpl (private val serverApi: ServerApi, private val apiKey: String): NetworkRepository {
    override suspend fun getFilmItems(): ResultWrapper<List<FilmItem>> {
        val data = mutableListOf<FilmItem>()
        try {
            for (page in 1..20)
            data.addAll(serverApi.getTopFilms(token = apiKey, page = page).transform())
        } catch (error: Throwable) {
            println(error.message)
        return    ResultWrapper.NetworkError
        }
        return ResultWrapper.Success(data)
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