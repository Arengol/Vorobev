package ru.tinkoff.vorobev.exam.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.vorobev.exam.PopularFilmsAdapter
import ru.tinkoff.vorobev.exam.databinding.FragmentMainBinding
import ru.tinkoff.vorobev.exam.viewmodel.PopularFilmsViewModelImpl
import ru.tinkoff.vorobev.exam.viewmodel.UiState

@AndroidEntryPoint
class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel by activityViewModels<PopularFilmsViewModelImpl>()
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
        binding.TryAgainButton.setOnClickListener {
            viewModel.getFilms()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.popularFilmsState.observe(this) {
            when(it){
                UiState.Error -> {
                    binding.popularButton.isVisible = false
                    binding.favoriteButton.isVisible = false
                    binding.recyclerView.isVisible = false
                    binding.errorIcon.isVisible = true
                    binding.errorTextView.isVisible = true
                    binding.TryAgainButton.isVisible = true
                    binding.FilmTopProgressBar.isVisible = false
                }
                UiState.Loading -> {
                    binding.popularButton.isVisible = false
                    binding.favoriteButton.isVisible = false
                    binding.recyclerView.isVisible = false
                    binding.errorIcon.isVisible = false
                    binding.errorTextView.isVisible = false
                    binding.TryAgainButton.isVisible = false
                    binding.FilmTopProgressBar.isVisible = true
                }
                UiState.Success -> {
                    binding.popularButton.isVisible = true
                    binding.favoriteButton.isVisible = true
                    binding.recyclerView.isVisible = true
                    binding.errorIcon.isVisible = false
                    binding.errorTextView.isVisible = false
                    binding.TryAgainButton.isVisible = false
                    binding.FilmTopProgressBar.isVisible = false
                    binding.recyclerView.adapter = PopularFilmsAdapter(viewModel)
                }
                UiState.Wait -> viewModel.getFilms()
            }
        }
    }
}