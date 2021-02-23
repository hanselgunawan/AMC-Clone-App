package com.hanseltritama.amccloneapp.request;

import com.hanseltritama.amccloneapp.utils.Credentials;
import com.hanseltritama.amccloneapp.utils.MovieAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieAPI movieApi = retrofit.create(MovieAPI.class);

    public static MovieAPI getMovieApi() {
        return movieApi;
    }
}
