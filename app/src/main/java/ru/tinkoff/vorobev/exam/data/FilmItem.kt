package ru.tinkoff.vorobev.exam.data

data class FilmItem(
    val filmId: Int,
    val posterUrlPreview: String,
    val nameRu: String,
    val genres: List<String>,
    val year: String
)