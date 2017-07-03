package com.surya.gamaliel.popular_movies;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private final static String API_KEY = "[You can fill this with your own API KEY]";
    private final static String BASE_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular";
    private final static String BASE_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated";
    private final static String PARAM_QUERY = "api_key";

    public static URL buildUrl(String sortOrder) {
        Uri builtUri = Uri.parse(
                (sortOrder.equals("Most Popular") ? BASE_URL_POPULAR : BASE_URL_TOP_RATED)
        )
                .buildUpon()
                .appendQueryParameter(PARAM_QUERY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
