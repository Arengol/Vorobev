package ru.tinkoff.vorobev.exam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.tinkoff.vorobev.exam.data.Film
import ru.tinkoff.vorobev.exam.data.FilmItem
import ru.tinkoff.vorobev.exam.network.NetworkRepository
import ru.tinkoff.vorobev.exam.network.ResultWrapper
import javax.inject.Inject

@HiltViewModel
class FilmViewModelImpl @Inject constructor (private val networkRepository: NetworkRepository
): FilmViewModel, ViewModel() {
    private val _popularFilmsState = MutableLiveData<UiState>(UiState.Wait)
    override val popularFilmsState: LiveData<UiState>
        get() = _popularFilmsState

    private val _popularFilmsData = MutableLiveData<List<FilmItem>>()
    override val popularFilmsData: LiveData<List<FilmItem>>
        get() = _popularFilmsData

    private val _filmState = MutableLiveData<UiState>(UiState.Wait)
    override val filmState: LiveData<UiState>
        get() = _filmState

    private val _filmsData = MutableLiveData<Film>()
    override val filmData: LiveData<Film>
        get() = _filmsData

    override var selectedFilm: Int = 0

    private val _searchFilmData = MutableLiveData<List<FilmItem>>()
    override val searchFilmData: LiveData<List<FilmItem>>
        get() = _searchFilmData

    override fun getFilms() {
        viewModelScope.launch{
            _popularFilmsState.value = UiState.Loading
            when(val result = networkRepository.getFilmItems()){
                is ResultWrapper.Success -> {
                    _popularFilmsData.value = result.value!!
                    _popularFilmsState.value = UiState.Success
                }
                is ResultWrapper.NetworkError -> _popularFilmsState.value = UiState.Error
            }
        }
    }

    override fun getFilmInfo() {
        viewModelScope.launch {
            _filmState.value = UiState.Loading
            when(val result = networkRepository.getFilmInfoById(selectedFilm)) {
                ResultWrapper.NetworkError -> _filmState.value = UiState.Error
                is ResultWrapper.Success -> {
                    _filmsData.value = result.value!!
                    _filmState.value = UiState.Success
                }
            }
        }
    }

    override fun searchFilm(searchRequest: String) {
        val searchBuffer = _popularFilmsData.value?.filter { it.nameRu.contains(searchRequest, ignoreCase = true) } ?: listOf()
        if (searchBuffer.isNullOrEmpty())
            _popularFilmsState.value = UiState.SearchError
        else {
            _searchFilmData.value = searchBuffer
            _popularFilmsState.value = UiState.SearchSuccess
        }
    }

    override fun normalizeState() {
        _popularFilmsState.value = UiState.Success
    }
}