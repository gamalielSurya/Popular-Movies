package com.surya.gamaliel.popular_movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String CLICKED_MOVIE = "clicked_movie";
    private static final String SAVED_MOVIE = "saved_movie";
    private static final String SAVED_SORT = "saved_sort";

    private ArrayList<Movie> movieData;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView tvErrorMessage;
    private String mSortOrder;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvErrorMessage = (TextView) findViewById(R.id.tvErrorMessage);

        loadingIndicator = (ProgressBar) findViewById(R.id.pb_loadingIndicator);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_movie);

        if (savedInstanceState != null) {
            movieData = savedInstanceState.getParcelableArrayList(SAVED_MOVIE);
            loadMovie(savedInstanceState.getString(SAVED_SORT));
        } else {
            loadMovie("Most Popular");
        }

        int numberOfColumns = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);
    }

    private void loadMovie(String sOrder) {
        mSortOrder = sOrder;
        new FetchMovieTask().execute(mSortOrder);
        Toast.makeText(MainActivity.this,
                (mSortOrder.equals("Most Popular") ? "Most Popular Movies" : "Top Rated Movies"),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();
        if (selectedItem == R.id.action_most_popular) {
            loadMovie("Most Popular");
        } else if (selectedItem == R.id.action_top_rated) {
            loadMovie("Top Rated");
        }
        return true;
    }

    private void showResult() {
        tvErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        tvErrorMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            if(params.length == 0) {
                return null;
            }

            String sortOrder = params[0];

            URL movieRequestedUrl = NetworkUtils.buildUrl(sortOrder);

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestedUrl);

                movieData = JSONUtils.getMovieFromJSON(MainActivity.this, jsonResponse);
                return  movieData;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> arrayList) {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if(arrayList != null) {
                showResult();
                mMovieAdapter.setmMovieData(arrayList);
            } else {
                showError();
            }
        }
    }

    @Override
    public void onClick(Movie mData) {
        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
        intent.putExtra(CLICKED_MOVIE, new
                Movie(mData.getTitle(), mData.getPoster(), mData.getOverview(), mData.getRelease(), mData.getVote()));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(SAVED_MOVIE, movieData);
        savedInstanceState.putString(SAVED_SORT, mSortOrder);
        super.onSaveInstanceState(savedInstanceState);
    }
}