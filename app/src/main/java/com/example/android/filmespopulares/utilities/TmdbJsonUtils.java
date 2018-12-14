package com.example.android.filmespopulares.utilities;

import com.example.android.filmespopulares.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TmdbJsonUtils {

    public static List<Movie> getMoviesFromJson(String moviesJsonStr)
            throws JSONException {

        final String TMDB_RESULTS = "results";

        final String TMDB_TITLE = "title";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_VOTE_AVERAGE = "vote_average";
        final String TMDB_VOTE_COUNT = "vote_count";
        final String TMDB_RELEASE_DATE = "release_date";

        List<Movie> movies = new ArrayList<>();

        JSONObject movieJsonObject = new JSONObject(moviesJsonStr);

        JSONArray movieArray = movieJsonObject.getJSONArray(TMDB_RESULTS);

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject currentMovie = movieArray.getJSONObject(i);

            String title = currentMovie.getString(TMDB_TITLE);
            String poster = currentMovie.getString(TMDB_POSTER_PATH);
            String synopsis = currentMovie.getString(TMDB_OVERVIEW);
            double averageRating = currentMovie.getDouble(TMDB_VOTE_AVERAGE);
            int ratingsCount = currentMovie.getInt(TMDB_VOTE_COUNT);
            String releaseDate = currentMovie.getString(TMDB_RELEASE_DATE);

            Movie movie = new Movie(title, poster, synopsis, averageRating, ratingsCount, releaseDate);
            movies.add(movie);
        }

        return movies;
    }
}