package ru.tinkoff.vorobev.exam.data.dto

data class Film (
    val posterUrl: String,
    val description: String,
    val nameRu: String,
    val genres: List<String>,
    val country: List<String>
)