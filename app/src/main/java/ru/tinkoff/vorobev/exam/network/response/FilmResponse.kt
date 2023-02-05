package ru.tinkoff.vorobev.exam.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmResponse(
    val filmId: Int,
    val nameRu: String? = null,
   // val nameEn: String? = null,
    val year: String? = null,
   // val filmLength: String? = null,
 //   val countries: List<CountryResponse> = emptyList(),
    val genres: List<GenreResponse> = emptyList(),
  //  val rating: Double? = null,
  //  val ratingVoteCount: Int? = null,
  //  val posterUrl: String,
    val posterUrlPreview: String
)
