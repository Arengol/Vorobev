package ru.tinkoff.vorobev.exam.UI.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.vorobev.exam.R
import ru.tinkoff.vorobev.exam.UI.FilmItemAdapter
import ru.tinkoff.vorobev.exam.UI.SearchFilmItemAdapter
import ru.tinkoff.vorobev.exam.databinding.FragmentMainBinding
import ru.tinkoff.vorobev.exam.viewmodel.FilmViewModelImpl
import ru.tinkoff.vorobev.exam.viewmodel.UiState

@AndroidEntryPoint
class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel by activityViewModels<FilmViewModelImpl>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tryAgainButton.setOnClickListener {
            viewModel.getFilms()
        }
        binding.searchButton.setOnClickListener {
            binding.popularButton.isVisible = false
            binding.favoriteButton.isVisible = false
            binding.searchButton.isVisible = false
            binding.textView.isVisible = false
            binding.searchText1.isVisible = true
            binding.imageBackButton.isVisible = true
        }
        binding.favoriteButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_mainFragment_to_filmFavoriteFragment)
        }
        binding.imageBackButton.setOnClickListener {
            binding.popularButton.isVisible = true
            binding.favoriteButton.isVisible = true
            binding.searchButton.isVisible = true
            binding.textView.isVisible = true
            binding.searchText1.isVisible = false
            binding.imageBackButton.isVisible = false
            binding.searchErrorTextView.isVisible = false
            binding.recyclerView.isVisible = true
            binding.recyclerView.adapter = FilmItemAdapter(viewModel)
        }
        binding.searchText1.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding.searchErrorTextView.isVisible = false
                    binding.recyclerView.isVisible = true
                    viewModel.searchFilm(binding.searchText1.text.toString())
                    return true
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.popularFilmsState.observe(this) {
            when(it){
                UiState.Error -> {
                    binding.popularButton.isVisible = true
                    binding.favoriteButton.isVisible = true
                    binding.recyclerView.isVisible = false
                    binding.errorIcon.isVisible = true
                    binding.errorTextView.isVisible = true
                    binding.tryAgainButton.isVisible = true
                    binding.filmTopProgressBar.isVisible = false
                }
                UiState.Loading -> {
                    binding.popularButton.isVisible = false
                    binding.favoriteButton.isVisible = false
                    binding.recyclerView.isVisible = false
                    binding.errorIcon.isVisible = false
                    binding.errorTextView.isVisible = false
                    binding.tryAgainButton.isVisible = false
                    binding.filmTopProgressBar.isVisible = true
                }
                UiState.Success -> {
                    binding.popularButton.isVisible = true
                    binding.favoriteButton.isVisible = true
                    binding.recyclerView.isVisible = true
                    binding.errorIcon.isVisible = false
                    binding.errorTextView.isVisible = false
                    binding.tryAgainButton.isVisible = false
                    binding.filmTopProgressBar.isVisible = false
                    binding.recyclerView.adapter = FilmItemAdapter(viewModel)
                }
                UiState.Wait -> viewModel.getFilms()
                UiState.SearchSuccess -> {
                    binding.recyclerView.adapter = SearchFilmItemAdapter(viewModel)
                }
                UiState.SearchError -> {
                    binding.recyclerView.isVisible = false
                    binding.searchErrorTextView.isVisible = true
                }
            }
        }
    }
}