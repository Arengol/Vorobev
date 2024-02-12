package ru.tinkoff.vorobev.exam.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Arrays
import java.util.stream.Collectors

@Entity(tableName = "favorite_films")
data class FavoriteFilmEntity (
    @PrimaryKey val filmId: Int,
    @ColumnInfo(name = "poster_url_preview") val posterUrlPreview: String,
    @ColumnInfo(name = "name_ru") val nameRu: String,
    @ColumnInfo(name = "genres") val genres: List<String>,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "posterUrl") val posterUrl: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "country") val country: List<String>
)

class Converters {
    @TypeConverter fun fromStringList(genres: List<String>): String {
        return genres.stream().collect(Collectors.joining(","))
    }
    @TypeConverter fun toStringList(data: String): List<String> {
        return data.split(",")
    }
}