package com.hanseltritama.amccloneapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hanseltritama.amccloneapp.model.MovieModel;
import com.hanseltritama.amccloneapp.request.Service;
import com.hanseltritama.amccloneapp.response.MovieSearchResponse;
import com.hanseltritama.amccloneapp.utils.Credentials;
import com.hanseltritama.amccloneapp.utils.MovieAPI;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getRetrofitResponse();
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
}