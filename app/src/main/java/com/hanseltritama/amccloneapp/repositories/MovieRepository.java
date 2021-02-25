package com.hanseltritama.amccloneapp.repositories;

import com.hanseltritama.amccloneapp.model.MovieModel;
import com.hanseltritama.amccloneapp.request.MovieAPIClient;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieRepository {

    // Singleton for repository
    private static MovieRepository instance;

    private MovieAPIClient movieAPIClient;

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieAPIClient = MovieAPIClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieAPIClient.getMovies();
    }

}
