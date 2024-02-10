package ru.tinkoff.vorobev.exam.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.tinkoff.vorobev.exam.network.response.FilmInfoResponse
import ru.tinkoff.vorobev.exam.network.response.FilmTopResponse

interface ServerAPI {
    @GET("top")
    suspend fun getTopFilms(@Header("X-API-KEY") token: String,
                            @Query("type") type: String = "TOP_100_POPULAR_FILMS",
                            @Query("page") page: Int
    ): FilmTopResponse

    @GET("{id}")
    suspend fun getFilmInfo(@Header("X-API-KEY") token: String,
                            @Path("id") id: Int
    ): FilmInfoResponse
}