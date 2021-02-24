package com.hanseltritama.amccloneapp.repositories;

import com.hanseltritama.amccloneapp.model.MovieModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieRepository {

    // Singleton for repository
    private static MovieRepository instance;

    // LiveData
    private MutableLiveData<List<MovieModel>> moviesLiveData;

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        moviesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return moviesLiveData;
    }

}
