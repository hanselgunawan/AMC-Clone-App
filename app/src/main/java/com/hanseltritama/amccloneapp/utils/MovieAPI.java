package com.hanseltritama.amccloneapp.utils;

import com.hanseltritama.amccloneapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPI {

    // Search for movies
    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovie(
        @Query("api_key") String key,
        @Query("query") String query,
        @Query("page") int page
    );
}
