package com.hanseltritama.amccloneapp.request;

import com.hanseltritama.amccloneapp.model.MovieModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieAPIClient {

    // LiveData
    private MutableLiveData<List<MovieModel>> moviesLiveData;

    private static MovieAPIClient instance;

    public static MovieAPIClient getInstance() {
        if (instance == null) {
            instance = new MovieAPIClient();
        }
        return instance;
    }

    private MovieAPIClient() {
        moviesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return moviesLiveData;
    }

}
