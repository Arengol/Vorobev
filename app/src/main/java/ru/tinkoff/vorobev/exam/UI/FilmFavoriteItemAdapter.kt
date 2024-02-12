package ru.tinkoff.vorobev.exam.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.tinkoff.vorobev.exam.R
import ru.tinkoff.vorobev.exam.viewmodel.FilmViewModel

class FilmFavoriteItemAdapter (private val viewModel: FilmViewModel):
    RecyclerView.Adapter<FilmFavoriteItemAdapter.FavoriteItemViewHolder>(){
    lateinit var context: Context
    class FavoriteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val PosterPreviewFilmImageView : ImageView = itemView.findViewById(R.id.PosterPreviewFilmImageView)
        val NameFilmTextView : TextView = itemView.findViewById(R.id.NameFilmTextView)
        val InfoFilmTextView : TextView = itemView.findViewById(R.id.InfoFilmTextView)
        val FavoriteFilmImageView : ImageView = itemView.findViewById(R.id.FavoriteFilmImageView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        context = parent.context
        return FavoriteItemViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return viewModel.popularFilmsData.value?.filter { it.isFavorite }?.size ?: 0
    }
    override fun onBindViewHolder(holder: FavoriteItemViewHolder, position: Int) {
        val mainGenre = viewModel.popularFilmsData.value?.filter { it.isFavorite }?.get(position)?.genres?.get(0) ?: ""
        val year = viewModel.popularFilmsData.value?.filter { it.isFavorite }?.get(position)?.year ?: ""
        holder.NameFilmTextView.text = viewModel.popularFilmsData.value?.filter { it.isFavorite }?.get(position)?.nameRu ?: ""
        holder.InfoFilmTextView.text = "${mainGenre.capitalize()} ($year)"
        holder.FavoriteFilmImageView.isVisible = viewModel.popularFilmsData.value?.filter { it.isFavorite }?.get(position)?.isFavorite ?: false
        Glide.with(context).load(viewModel.popularFilmsData.value?.filter { it.isFavorite }?.get(position)?.posterUrlPreview).into(holder.PosterPreviewFilmImageView)
        holder.itemView.setOnClickListener {
            viewModel.selectedFilm =
                viewModel.popularFilmsData.value!!.filter { it.isFavorite }.get(position)
            holder.itemView.findNavController().navigate(R.id.action_filmFavoriteFragment_to_filmInfoFragment)
        }
        holder.itemView.setOnLongClickListener{
            if (!viewModel.popularFilmsData.value!!.filter { it.isFavorite }.get(position).isFavorite){
                holder.FavoriteFilmImageView.isVisible = true
                viewModel.addFavoriteFilm(viewModel.popularFilmsData.value!!.filter { it.isFavorite }.get(position))
            }
            else{
                holder.FavoriteFilmImageView.isVisible = false
                viewModel.deleteFavoriteFilm(viewModel.popularFilmsData.value!!.filter { it.isFavorite }.get(position))
            }
            true
        }
    }

}