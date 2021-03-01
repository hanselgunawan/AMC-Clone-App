package com.hanseltritama.amccloneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanseltritama.amccloneapp.model.MovieModel;

public class MovieDetailsActivity extends AppCompatActivity {

    // Widgets
    private ImageView movieImageViewDetails;
    private TextView movieTitleDetails;
    private RatingBar movieRatingBarDetails;
    private TextView movieDescDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieImageViewDetails = findViewById(R.id.movie_image_view_details);
        movieTitleDetails = findViewById(R.id.movie_title_details);
        movieRatingBarDetails = findViewById(R.id.movie_rating_bar_details);
        movieDescDetails = findViewById(R.id.movie_desc_details);

        getDataFromIntent();

    }

    private void getDataFromIntent() {
        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            movieTitleDetails.setText(movieModel.getTitle());
            movieDescDetails.setText(movieModel.getMovie_overview());
            movieRatingBarDetails.setRating(movieModel.getVote_average());

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"
                    + movieModel.getPoster_path())
                    .into(movieImageViewDetails);
        }
    }
}