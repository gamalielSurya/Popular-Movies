package com.surya.gamaliel.popular_movies;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

final class JSONUtils {

    private static final String MOV_RESULTS = "results";
    private static final String MOV_TITLE = "title";
    private static final String MOV_POSTER = "poster_path";
    private static final String MOV_OVERVIEW = "overview";
    private static final String MOV_RELEASE_DATE = "release_date";
    private static final String MOV_VOTE_AVERAGE = "vote_average";

    public static ArrayList<Movie> getMovieFromJSON(Context context, String movieJsonStr) throws JSONException {

        JSONObject movieJson = new JSONObject(movieJsonStr);

        JSONArray movieArray = movieJson.getJSONArray(MOV_RESULTS);

        ArrayList<Movie> pMovies = new ArrayList<>();

        for(int i=0; i < movieArray.length(); i++) {
            JSONObject movieObject = movieArray.getJSONObject(i);
            String movTitle = movieObject.getString(MOV_TITLE);
            String movPoster = movieObject.getString(MOV_POSTER);
            String movOverView = movieObject.getString(MOV_OVERVIEW);
            String movReleaseDate = movieObject.getString(MOV_RELEASE_DATE);
            String movVoteAverage = movieObject.getString(MOV_VOTE_AVERAGE);

            pMovies.add(i, new Movie(movTitle, movPoster, movOverView, movReleaseDate, movVoteAverage));
        }

        return pMovies;
    }
}