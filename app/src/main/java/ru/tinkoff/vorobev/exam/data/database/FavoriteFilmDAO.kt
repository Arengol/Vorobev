package ru.tinkoff.vorobev.exam.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteFilmDAO {
    @Query("SELECT * FROM favorite_films")
    fun getAll(): List<FavoriteFilmEntity>
    @Query("SELECT * FROM favorite_films WHERE filmId LIKE :favoriteFilmId")
    fun getFavoriteFilm (favoriteFilmId: Int): FavoriteFilmEntity
    @Insert
    fun addFavoriteFilm (favoriteFilmEntity: FavoriteFilmEntity)
    @Query("DELETE FROM favorite_films WHERE filmId LIKE :favoriteFilmId")
    fun deleteFavoriteFilm (favoriteFilmId: Int)
}