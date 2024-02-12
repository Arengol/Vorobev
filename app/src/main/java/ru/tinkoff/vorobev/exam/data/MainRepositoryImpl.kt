package ru.tinkoff.vorobev.exam.data

import ru.tinkoff.vorobev.exam.data.database.DatabaseRepository
import ru.tinkoff.vorobev.exam.data.dto.Film
import ru.tinkoff.vorobev.exam.data.dto.FilmItem
import ru.tinkoff.vorobev.exam.data.network.NetworkRepository
import ru.tinkoff.vorobev.exam.data.network.ResultWrapper
import javax.inject.Inject

class MainRepositoryImpl (private val networkRepository: NetworkRepository,
                                            private val databaseRepository: DatabaseRepository
    ): MainRepository {
    override suspend fun getAllFilms(): List<FilmItem> {
        lateinit var allFilms: List<FilmItem>
        when(val filmWrapper = networkRepository.getFilmItems()){
            is ResultWrapper.Success -> allFilms = filmWrapper.value
            is ResultWrapper.NetworkError -> throw Exception("NetworkError")
        }
        var favoriteFilms = databaseRepository.getAllFavoriteFilm()
        allFilms.forEach {
            if (favoriteFilms.contains(it)){
                it.isFavorite = true
            }
        }
        return allFilms
    }

    override suspend fun getAllFavoriteFilms(): List<FilmItem> {
        return databaseRepository.getAllFavoriteFilm()
    }

    override suspend fun getFilmInfo(filmItem: FilmItem): Film {
        if (filmItem.isFavorite) {
        return    databaseRepository.getFavoriteFilmIfo(filmItem)
        } else return when (val filmInfo = networkRepository.getFilmInfoById(filmItem.filmId)){
            is ResultWrapper.Success -> return filmInfo.value
            is ResultWrapper.NetworkError -> throw Exception("NetworkError")
        }
    }

    override suspend fun addFavoriteFilm(filmItem: FilmItem) {
        when(val filmInfo = networkRepository.getFilmInfoById(filmItem.filmId)){
            is ResultWrapper.Success -> {
                databaseRepository.insertFavoriteFilm(filmItem, filmInfo.value)
            }
            is ResultWrapper.NetworkError -> {
            }
        }
    }

    override suspend fun deleteFavoriteFilm(filmItem: FilmItem) {
        databaseRepository.deleteFavoriteFilm(filmItem)
    }
}