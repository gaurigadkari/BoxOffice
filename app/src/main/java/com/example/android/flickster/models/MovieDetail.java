package com.example.android.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetail {
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", backdropPath);
    }
    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public double getRating() {
        return rating;
    }

    ArrayList<String> genres =new ArrayList<String>();
    String homepage;
    double rating;
    String description;

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    String title;
    String backdropPath;
    JSONArray genresArray = null;
    public MovieDetail(JSONObject jsonObject) throws JSONException {
        homepage = jsonObject.getString("homepage");
        rating = jsonObject.getDouble("vote_average");
        description = jsonObject.getString("overview");
        title = jsonObject.getString("original_title");
        backdropPath = jsonObject.getString("backdrop_path");
        genresArray = jsonObject.getJSONArray("genres");
        int length = genresArray.length();
        for(int i = 0; i <length; i++) {
            try {
                genres.add(genresArray.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public String getGenre() {
        String genreList="";
        int length = genres.size();
        for(int i=0; i < length; i++) {
            if(i == 0) {
                genreList = genres.get(i);
            }
            else {
                genreList = genreList + ", "+genres.get(i);
            }
        }
        return genreList;
    }


}
