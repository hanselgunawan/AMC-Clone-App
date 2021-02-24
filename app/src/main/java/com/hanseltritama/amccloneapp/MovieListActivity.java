package com.hanseltritama.amccloneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hanseltritama.amccloneapp.model.MovieModel;
import com.hanseltritama.amccloneapp.request.Service;
import com.hanseltritama.amccloneapp.response.MovieSearchResponse;
import com.hanseltritama.amccloneapp.utils.Credentials;
import com.hanseltritama.amccloneapp.utils.MovieAPI;
import com.hanseltritama.amccloneapp.viewmodel.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    Button btn;

    // ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

//        btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                getRetrofitResponseById();
//            }
//        });
    }

    // Observing any data change
    private void setupObserver() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

            }
        });
    }

    private void getRetrofitResponse() {
        MovieAPI movieAPI = Service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieAPI
                .searchMovie(
                        Credentials.API_KEY,
                        "Jack Reacher",
                        1
                );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    Log.v("Tag", "Response: " + response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovieList());

                    for (MovieModel movie: movies) {
                        Log.v("Tag", "The Release Date: " + movie.getRelease_date());
                    }
                } else {
                    try {
                        Log.v("Tag", "Error " + response.errorBody().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    private void getRetrofitResponseById() {
        MovieAPI movieAPI = Service.getMovieApi();
        Call<MovieModel> movieModelCall = movieAPI.getMovie(343611, Credentials.API_KEY);

        movieModelCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    MovieModel movie = response.body();
                    Log.v("Tag", "The Response: " + movie.getTitle());
                } else {
                    try {
                        Log.v("Tag", "Error " + response.errorBody());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
}