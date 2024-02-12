package ru.tinkoff.vorobev.exam.data.network.response

import com.squareup.moshi.JsonClass
import ru.tinkoff.vorobev.exam.data.dto.Film

@JsonClass(generateAdapter = true)
data class FilmInfoResponse(
    val posterUrl: String,
    val description: String? = null,
    val nameRu: String? = null,
    val genres: List<GenreResponse> = emptyList(),
    val countries: List<CountryResponse> = emptyList()
): Transformable<Film> {
    override fun transform(): Film {
        val genres = mutableListOf<String>()
        val country = mutableListOf<String>()
        this.genres.forEach{
            genres.add(it.genre)
        }
        this.countries.forEach{
            country.add(it.country)
        }
        return Film(
            posterUrl = this.posterUrl,
            description = this.description ?: "",
            nameRu = this.nameRu ?: "",
            genres = genres,
            country = country
        )
    }
}