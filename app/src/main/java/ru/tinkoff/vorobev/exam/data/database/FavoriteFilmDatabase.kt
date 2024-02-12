package ru.tinkoff.vorobev.exam.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteFilmEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class FavoriteFilmDatabase : RoomDatabase() {
    abstract fun favoriteFilmDAO(): FavoriteFilmDAO
}