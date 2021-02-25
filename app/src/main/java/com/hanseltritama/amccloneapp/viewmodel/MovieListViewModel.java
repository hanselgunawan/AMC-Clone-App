package com.hanseltritama.amccloneapp.viewmodel;

import com.hanseltritama.amccloneapp.model.MovieModel;
import com.hanseltritama.amccloneapp.repositories.MovieRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    // LiveData Getter
    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    public void searchMovieApi(String query, int pageNumber) {
        movieRepository.searchMovieAPI(query, pageNumber);
    }

}
