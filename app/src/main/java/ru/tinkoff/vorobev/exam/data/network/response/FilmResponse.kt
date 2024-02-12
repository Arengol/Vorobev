package ru.tinkoff.vorobev.exam.data.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmResponse(
    val filmId: Int,
    val nameRu: String? = null,
    val year: String? = null,
    val genres: List<GenreResponse> = emptyList(),
    val posterUrlPreview: String
)