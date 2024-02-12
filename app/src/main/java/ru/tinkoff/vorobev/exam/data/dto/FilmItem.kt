package ru.tinkoff.vorobev.exam.data.dto

import ru.tinkoff.vorobev.exam.data.database.FavoriteFilmDAO

data class FilmItem(
    val filmId: Int,
    val posterUrlPreview: String,
    val nameRu: String,
    val genres: List<String>,
    val year: String,
    var isFavorite: Boolean = false
)