package ru.tinkoff.vorobev.exam.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryResponse(
    val country: String
)