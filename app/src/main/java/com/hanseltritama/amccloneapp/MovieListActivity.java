package com.hanseltritama.amccloneapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hanseltritama.amccloneapp.adapters.MovieRecyclerView;
import com.hanseltritama.amccloneapp.adapters.OnMovieListener;
import com.hanseltritama.amccloneapp.model.MovieModel;
import com.hanseltritama.amccloneapp.request.Service;
import com.hanseltritama.amccloneapp.response.MovieSearchResponse;
import com.hanseltritama.amccloneapp.utils.Credentials;
import com.hanseltritama.amccloneapp.utils.MovieAPI;
import com.hanseltritama.amccloneapp.viewmodel.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    // Speech to Text Button
    Button speechToText;

    // Search View
    SearchView searchView;

    // Progress Bar
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;

    //RecyclerView
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerViewAdapter;

    // ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button Speech to Text
        speechToText = findViewById(R.id.speech_to_text_button);

        // Search View
        searchView = findViewById(R.id.search_view);

        // Constraint Layout
        constraintLayout = findViewById(R.id.constraint_layout);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Search View
        SetupSearchView();

        // Progress Bar
        progressBar = findViewById(R.id.my_progress_bar);
        progressBar.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.my_recycler_view);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        // Voice Search
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        ConfigureRecyclerView();

        // Speech to Text
        speechToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });

        setupObserver();

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchMovieApi("Fast", 1);
//            }
//        });
//        btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                getRetrofitResponseById();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            searchView.setQuery(result.get(0), true);
        }
    }

    private void askSpeechInput() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition is not available", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");
            startActivityForResult(intent, 102);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();
    }

    // Observing any data change
    private void setupObserver() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    for (MovieModel movieModel: movieModels) {
                        // Get the data in the log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                        movieRecyclerViewAdapter.setMovieModels(movieModels);
                        progressBar.setVisibility(View.GONE);
                        constraintLayout.setBackgroundColor(ContextCompat.getColor(
                                MovieListActivity.this,
                                R.color.transparent)
                        );
                    }
                }
            }
        });
    }

    // Initializing recyclerView & adding data to it
    private void ConfigureRecyclerView() {
        movieRecyclerViewAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // RecyclerView Pagination
        // Loading next page of API response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                // Check if recyclerView can still scroll vertically or not (hit the last item)
                if (!recyclerView.canScrollVertically(1)) {
                    // Display the next result
                    movieListViewModel.searchNextPage();
                    progressBar.setVisibility(View.VISIBLE);
                    constraintLayout.setBackgroundColor(ContextCompat.getColor(
                            MovieListActivity.this,
                            R.color.black_50_percent_opactiy)
                    );
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", movieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    // Get data from searchview & query the API to get the results
    private void SetupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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

    private void getRetrofitResponseById() {
        MovieAPI movieAPI = Service.getMovieApi();
        Call<MovieModel> movieModelCall = movieAPI.getMovie(343611, Credentials.API_KEY);

        movieModelCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    MovieModel movie = response.body();
                    Log.v("Tag", "The Response: " + movie.getTitle());
                } else {
                    try {
                        Log.v("Tag", "Error " + response.errorBody());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
}