package ru.tinkoff.vorobev.exam.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.tinkoff.vorobev.exam.R
import ru.tinkoff.vorobev.exam.data.FilmItem
import ru.tinkoff.vorobev.exam.viewmodel.FilmViewModel

class SearchFilmItemAdapter (private val viewModel: FilmViewModel):
        RecyclerView.Adapter<SearchFilmItemAdapter.ItemViewHolder>(){
            lateinit var context: Context
            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val PosterPreviewFilmImageView : ImageView = itemView.findViewById(R.id.PosterPreviewFilmImageView)
                val NameFilmTextView : TextView = itemView.findViewById(R.id.NameFilmTextView)
                val InfoFilmTextView : TextView = itemView.findViewById(R.id.InfoFilmTextView)
                val FavoriteFilmImageView : ImageView = itemView.findViewById(R.id.FavoriteFilmImageView)
            }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilmItemAdapter.ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        context = parent.context
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return viewModel.searchFilmData.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val mainGenre = viewModel.searchFilmData.value?.get(position)?.genres?.get(0) ?: ""
        val year = viewModel.searchFilmData.value?.get(position)?.year ?: ""
        holder.NameFilmTextView.text = viewModel.searchFilmData.value?.get(position)?.nameRu ?: ""
        holder.InfoFilmTextView.text = "${mainGenre.capitalize()} ($year)"
        Glide.with(context).load(viewModel.searchFilmData.value?.get(position)?.posterUrlPreview).into(holder.PosterPreviewFilmImageView)
        holder.itemView.setOnClickListener {
            viewModel.selectedFilm =
                viewModel.searchFilmData.value!!.get(position).filmId
            holder.itemView.findNavController().navigate(R.id.action_mainFragment_to_filmInfoFragment)
        }
    }
}