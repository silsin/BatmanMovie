package com.fakhari.batmanmovie.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fakhari.batmanmovie.R
import com.fakhari.batmanmovie.data.model.SearchMovieModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieListAdapter(val adapterOnClick: (String) -> Unit) :
    ListAdapter<SearchMovieModel, MovieListAdapter.MovieViewHolder>(POST_COMPARATOR) {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater.inflate(R.layout.movie_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load(getItem(position)?.poster)
            .into(holder.itemView.movie_poster)

        holder.itemView.setOnClickListener { adapterOnClick(getItem(position).imdbId) }
    }

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<SearchMovieModel>() {
            override fun areItemsTheSame(
                oldItem: SearchMovieModel,
                newItem: SearchMovieModel
            ): Boolean =
                oldItem.imdbId == newItem.imdbId

            override fun areContentsTheSame(
                oldItem: SearchMovieModel,
                newItem: SearchMovieModel
            ): Boolean =
                oldItem == newItem
        }
    }
}