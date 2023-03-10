package ru.tinkoff.vorobev.exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.vorobev.exam.data.FilmItem
import ru.tinkoff.vorobev.exam.databinding.ActivityMainBinding
import ru.tinkoff.vorobev.exam.network.NetworkRepository
import ru.tinkoff.vorobev.exam.network.NetworkRepositoryImpl
import ru.tinkoff.vorobev.exam.network.ResultWrapper
import java.io.Console
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    @Inject lateinit var networkService: NetworkRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(binding.root)
    }
}