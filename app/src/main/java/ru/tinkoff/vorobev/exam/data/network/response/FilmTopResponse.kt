package ru.tinkoff.vorobev.exam.data.network.response

import com.squareup.moshi.JsonClass
import ru.tinkoff.vorobev.exam.data.dto.FilmItem

@JsonClass(generateAdapter = true)
data class FilmTopResponse(
    val pagesCount: Int,
    val films: List<FilmResponse> = emptyList()
): Transformable<List<FilmItem>> {
    override fun transform(): List<FilmItem> {
        val result = mutableListOf<FilmItem>()
        this.films.forEach{
            val genres = mutableListOf<String>()
            it.genres.forEach{
                genres.add(it.genre)
            }
            result.add(
                FilmItem(
                filmId = it.filmId,
                posterUrlPreview = it.posterUrlPreview,
                nameRu = it.nameRu ?: "",
                genres = genres,
                year = it.year ?: ""
            )
            )
        }
        return result
    }
}