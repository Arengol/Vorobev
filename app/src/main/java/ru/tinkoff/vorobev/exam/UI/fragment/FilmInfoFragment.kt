package ru.tinkoff.vorobev.exam.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.vorobev.exam.R
import ru.tinkoff.vorobev.exam.databinding.FragmentFilmInformationBinding
import ru.tinkoff.vorobev.exam.viewmodel.FilmViewModelImpl
import ru.tinkoff.vorobev.exam.viewmodel.UiState

@AndroidEntryPoint
class FilmInfoFragment: Fragment() {
    private lateinit var binding: FragmentFilmInformationBinding
    private val viewModel by activityViewModels<FilmViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFilmInfo()
        binding.imageBackButton.setOnClickListener {
            viewModel.normalizeState()
            binding.root.findNavController().navigate(R.id.action_filmInfoFragment_to_mainFragment)
        }
        binding.filmInfoTryAgainButton.setOnClickListener {
            viewModel.getFilmInfo()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.filmState.observe(this) {
            when(it){
                UiState.Error -> {
                    binding.filmInfoErrorIcon.isVisible = true
                    binding.filmInfoErrorTextView.isVisible = true
                    binding.filmInfoTryAgainButton.isVisible = true
                    binding.posterFilmImageView.isVisible = false
                    binding.filmTextView.isVisible = false
                    binding.descriptionFilmTextView.isVisible = false
                    binding.genreFilmTextView.isVisible = false
                    binding.countryFilmTextView.isVisible = false
                    binding.filmInfoProgressBar.isVisible = false
                }
                UiState.Loading -> {
                    binding.filmInfoErrorIcon.isVisible = false
                    binding.filmInfoErrorTextView.isVisible = false
                    binding.filmInfoTryAgainButton.isVisible = false
                    binding.posterFilmImageView.isVisible = false
                    binding.filmTextView.isVisible = false
                    binding.descriptionFilmTextView.isVisible = false
                    binding.genreFilmTextView.isVisible = false
                    binding.countryFilmTextView.isVisible = false
                    binding.filmInfoProgressBar.isVisible = true
                }
                UiState.Success -> {
                    binding.filmInfoErrorIcon.isVisible = false
                    binding.filmInfoErrorTextView.isVisible = false
                    binding.filmInfoTryAgainButton.isVisible = false
                    binding.posterFilmImageView.isVisible = true
                    binding.filmTextView.isVisible = true
                    binding.descriptionFilmTextView.isVisible = true
                    binding.genreFilmTextView.isVisible = true
                    binding.countryFilmTextView.isVisible = true
                    binding.filmInfoProgressBar.isVisible = false

                    Glide.with(binding.root).load(viewModel.filmData.value!!.posterUrl).into(binding.posterFilmImageView)
                    binding.filmTextView.text = viewModel.filmData.value?.nameRu ?: ""
                    binding.descriptionFilmTextView.text = viewModel.filmData.value?.description ?: ""
                    binding.genreFilmTextView.text = "Жанры: " + viewModel.filmData.value?.genres?.joinToString(", ")
                    binding.countryFilmTextView.text = "Страны: " + viewModel.filmData.value?.country?.joinToString(", ")

                }
                UiState.Wait -> {}
            }
        }
    }
}