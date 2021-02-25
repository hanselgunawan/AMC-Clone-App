package com.hanseltritama.amccloneapp.request;

import android.util.Log;

import com.hanseltritama.amccloneapp.AppExecutors;
import com.hanseltritama.amccloneapp.model.MovieModel;
import com.hanseltritama.amccloneapp.response.MovieSearchResponse;
import com.hanseltritama.amccloneapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class MovieAPIClient {

    // LiveData
    private MutableLiveData<List<MovieModel>> moviesLiveData;
    private static MovieAPIClient instance;

    // Global RUNNABLE Request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

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

    public void searchMovieAPI(String query, int pageNumber) {
        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling Retrofit Call
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MICROSECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        private boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            // Getting the response objects
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovieList());
                    if (pageNumber == 1) {
                        // Sending data to live data
                        // PostValue: used for background thread
                        // setValue: not for background thread
                        moviesLiveData.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = moviesLiveData.getValue();
                        currentMovies.addAll(list);
                        moviesLiveData.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    moviesLiveData.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                moviesLiveData.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Service.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

}
