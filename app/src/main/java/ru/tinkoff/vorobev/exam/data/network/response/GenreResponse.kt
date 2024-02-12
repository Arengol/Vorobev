package ru.tinkoff.vorobev.exam.data.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResponse(
    val genre: String
)