package ru.tinkoff.vorobev.exam.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.vorobev.exam.R
import ru.tinkoff.vorobev.exam.databinding.FragmentFilmInfoBinding
import ru.tinkoff.vorobev.exam.viewmodel.PopularFilmsViewModelImpl
import ru.tinkoff.vorobev.exam.viewmodel.UiState

@AndroidEntryPoint
class FilmInfoFragment: Fragment() {
    private lateinit var binding: FragmentFilmInfoBinding
    private val viewModel by activityViewModels<PopularFilmsViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFilmInfo()
        binding.ImageBackButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_filmInfoFragment_to_mainFragment)
        }
        binding.TryAgainButton1.setOnClickListener {
            viewModel.getFilmInfo()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.filmState.observe(this){
            when(it){
                UiState.Error -> {
                    binding.errorIcon1.isVisible = true
                    binding.errorTextView1.isVisible = true
                    binding.TryAgainButton1.isVisible = true
                    binding.PosterFilmImageView.isVisible = false
                    binding.FilmTextView.isVisible = false
                    binding.DescriptionFilmTextView.isVisible = false
                    binding.GenreFilmTextView.isVisible = false
                    binding.CountryFilmTextView.isVisible = false
                    binding.FilmInfoProgressBar.isVisible = false
                }
                UiState.Loading -> {
                    binding.errorIcon1.isVisible = false
                    binding.errorTextView1.isVisible = false
                    binding.TryAgainButton1.isVisible = false
                    binding.PosterFilmImageView.isVisible = false
                    binding.FilmTextView.isVisible = false
                    binding.DescriptionFilmTextView.isVisible = false
                    binding.GenreFilmTextView.isVisible = false
                    binding.CountryFilmTextView.isVisible = false
                    binding.FilmInfoProgressBar.isVisible = true
                }
                UiState.Success -> {
                    binding.errorIcon1.isVisible = false
                    binding.errorTextView1.isVisible = false
                    binding.TryAgainButton1.isVisible = false
                    binding.PosterFilmImageView.isVisible = true
                    binding.FilmTextView.isVisible = true
                    binding.DescriptionFilmTextView.isVisible = true
                    binding.GenreFilmTextView.isVisible = true
                    binding.CountryFilmTextView.isVisible = true
                    binding.FilmInfoProgressBar.isVisible = false
                    Glide.with(binding.root).load(viewModel.filmData.value!!.posterUrl).into(binding.PosterFilmImageView)
                    binding.FilmTextView.text = viewModel.filmData.value?.nameRu ?: ""
                    binding.DescriptionFilmTextView.text = viewModel.filmData.value?.description ?: ""
                    binding.GenreFilmTextView.text = "Жанры: " + viewModel.filmData.value?.genres?.joinToString(", ")
                    binding.CountryFilmTextView.text = "Страны: " + viewModel.filmData.value?.country?.joinToString(", ")

                }
                UiState.Wait -> {}
            }
        }
    }
}