package com.hanseltritama.amccloneapp.request;

import com.hanseltritama.amccloneapp.AppExecutors;
import com.hanseltritama.amccloneapp.model.MovieModel;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

    public void searchMovieAPI() {
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                // Retrieve Data from API

            }
        });

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling Retrofit Call
                myHandler.cancel(true);
            }
        }, 4000, TimeUnit.MICROSECONDS);
    }

}
