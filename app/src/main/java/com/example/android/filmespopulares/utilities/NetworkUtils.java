package com.example.android.filmespopulares.utilities;

import android.net.Uri;

import com.example.android.filmespopulares.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Utilitários para estabelecer a conexão com a rede.
 */
public class NetworkUtils {

    final static String TMDB_BASE_URL =
            "http://api.themoviedb.org/3/movie/";

    final static String TMDB_BASE_POSTER_URL =
            "http://image.tmdb.org/t/p/";

    // Paramêtros da TMDB API para indicar a ordenação dos filmes
    public final static String SORT_BY_POPULAR = "popular";
    public final static String SORT_BY_TOP_RATED = "top_rated";

    final static String APPID_PARAM = "api_key";

    /**
     * Contrói a URL para consultar o TMDB
     *
     * @param sortBy indica a ordenação dos filmes.
     * @return URL para consultar o TMDB.
     */
    public static URL buildUrl(String sortBy) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(APPID_PARAM, BuildConfig.TMDB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Contrói a URL para acessar o poster de um filme
     *
     * @param filename  nome do arquivo do poster.
     * @param thumbSize tamanho do arquivo do poster.
     * @return URL para consultar o TMDB.
     */
    public static URL buildPosterUrl(String filename, String thumbSize) {
        Uri builtUri = Uri.parse(TMDB_BASE_POSTER_URL).buildUpon()
                .appendPath("w" + thumbSize)
                .appendEncodedPath(filename)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Retorna a string JSON com o resultado da consulta ao TMDB.
     */
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
        } finally {
            urlConnection.disconnect();
        }
    }
}