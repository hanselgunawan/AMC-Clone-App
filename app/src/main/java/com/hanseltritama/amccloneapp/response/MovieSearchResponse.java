package com.hanseltritama.amccloneapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hanseltritama.amccloneapp.model.MovieModel;

import java.util.List;

// This class is for getting multiple popular movies
public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose
    public int total_count;

    @SerializedName("results")
    @Expose
    public List<MovieModel> movieList;

    public int getTotal_count() {
        return total_count;
    }

    public List<MovieModel> getMovieList() {
        return movieList;
    }

}
