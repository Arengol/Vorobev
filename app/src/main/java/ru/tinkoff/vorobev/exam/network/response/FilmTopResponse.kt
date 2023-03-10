package ru.tinkoff.vorobev.exam.network.response

import com.squareup.moshi.JsonClass
import ru.tinkoff.vorobev.exam.data.FilmItem
import ru.tinkoff.vorobev.exam.network.Transformable

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
            result.add(FilmItem(
                filmId = it.filmId,
                posterUrlPreview = it.posterUrlPreview,
                nameRu = it.nameRu ?: "",
                genres = genres,
                year = it.year ?: ""
            ))
        }
        return result
    }
}


