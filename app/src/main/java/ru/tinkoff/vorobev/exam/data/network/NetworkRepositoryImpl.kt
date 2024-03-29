package ru.tinkoff.vorobev.exam.data.network

import ru.tinkoff.vorobev.exam.data.dto.Film
import ru.tinkoff.vorobev.exam.data.dto.FilmItem

class NetworkRepositoryImpl (private val serverApi: ServerAPI, private val apiKey: String):
    NetworkRepository {
    override suspend fun getFilmItems(): ResultWrapper<List<FilmItem>> {
        val data = mutableListOf<FilmItem>()
        try {
            for (page in 1..35){
                data.addAll(serverApi.getTopFilms(token = apiKey, page = page).transform())
                Thread.sleep(200)
            }
        } catch (error: Throwable) {
            return ResultWrapper.NetworkError
        }
        return ResultWrapper.Success(data)
    }
    override suspend fun getFilmInfoById(id: Int): ResultWrapper<Film> =
        try {
            ResultWrapper.Success(serverApi.getFilmInfo(token = apiKey, id = id).transform())
        }
        catch (error: Throwable){
            ResultWrapper.NetworkError
        }
}