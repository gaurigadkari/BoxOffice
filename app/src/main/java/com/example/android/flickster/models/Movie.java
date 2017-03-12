package com.example.android.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Movie {
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getOverview() {
        return overview;
    }
    public boolean isPopular(){
        return rating>5.0;
    }

    String posterPath;
    String originalTitle;
    String overview;
    String backdropPath;

    public int getMovieID() {
        return movieID;
    }

    int movieID;
    double rating;

    public Movie(JSONObject jsonObject) throws JSONException{
        posterPath = jsonObject.getString("poster_path");
        originalTitle = jsonObject.getString("original_title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        rating = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getInt("id");
    }

    public static ArrayList<Movie> fromJSonArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();
        int length = array.length();
        for(int i = 0; i <length; i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}

