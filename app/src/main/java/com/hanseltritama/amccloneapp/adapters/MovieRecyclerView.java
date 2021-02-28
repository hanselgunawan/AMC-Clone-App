package com.hanseltritama.amccloneapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hanseltritama.amccloneapp.R;
import com.hanseltritama.amccloneapp.model.MovieModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> movieModels;
    private OnMovieListener onMovieListener;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieViewHolder) holder).title.setText(movieModels.get(position).getTitle());
        ((MovieViewHolder) holder).release_date.setText(movieModels.get(position).getRelease_date());
        ((MovieViewHolder) holder).duration.setText(movieModels.get(position).getOriginal_language());

        // vote average is over 10, and our rating bar is only 5 stars: dividing by 2 in order to get the correct result
        ((MovieViewHolder) holder).ratingBar.setRating(movieModels.get(position).getVote_average()/2);

        // ImageView: using Glide library
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        + movieModels.get(position).getPoster_path())
                .into(((MovieViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        if (movieModels != null) {
            return movieModels.size();
        }
        return 0;
    }

    public void setMovieModels(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
        notifyDataSetChanged();
    }
}
