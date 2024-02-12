package ru.tinkoff.vorobev.exam.data.database

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.tinkoff.vorobev.exam.data.dto.Film
import ru.tinkoff.vorobev.exam.data.dto.FilmItem

class DatabaseRepositoryImpl(private val context: Context): DatabaseRepository {
    private val favoriteFilmDatabase = Room.databaseBuilder(context, FavoriteFilmDatabase::class.java, "favorite_films_db").allowMainThreadQueries().build()
    private val dao = favoriteFilmDatabase.favoriteFilmDAO()
    override suspend fun getAllFavoriteFilm(): List<FilmItem> {
        val favoriteFilmEntity = dao.getAll()
        val resultList = mutableListOf<FilmItem>()
        favoriteFilmEntity.forEach{
            resultList.add(
                FilmItem(
                filmId = it.filmId,
                posterUrlPreview = it.posterUrlPreview,
                nameRu = it.nameRu,
                genres = it.genres,
                year = it.year
            )
            )
        }
        return resultList
    }

    override suspend fun getFavoriteFilmIfo(filmItem: FilmItem): Film {
        val favoriteFilmEntity = dao.getFavoriteFilm(filmItem.filmId)
        return Film(
            posterUrl = favoriteFilmEntity.posterUrl,
            country = favoriteFilmEntity.country,
            description = favoriteFilmEntity.description,
            nameRu = favoriteFilmEntity.nameRu,
            genres = favoriteFilmEntity.genres
        )
    }

    override suspend fun insertFavoriteFilm(filmItem: FilmItem, film: Film) {
        dao.addFavoriteFilm(
            FavoriteFilmEntity(
            filmId = filmItem.filmId,
            posterUrlPreview = filmItem.posterUrlPreview,
            nameRu = filmItem.nameRu,
            genres = filmItem.genres,
            year = filmItem.year,
            posterUrl = film.posterUrl,
            description = film.description,
            country = film.country
        )
        )
        Glide.with(context).load(filmItem.posterUrlPreview).apply(
            RequestOptions.diskCacheStrategyOf(
                DiskCacheStrategy.ALL)).submit()
        Glide.with(context).load(film.posterUrl).apply(
            RequestOptions.diskCacheStrategyOf(
                DiskCacheStrategy.ALL)).submit()
    }

    override suspend fun deleteFavoriteFilm(filmItem: FilmItem) {
        dao.deleteFavoriteFilm(filmItem.filmId)
    }
}