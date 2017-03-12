package com.example.android.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.flickster.models.MovieDetail;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DetailsActivity extends AppCompatActivity{
    @BindView(R.id.ivMovieVideo) ImageView posterImage;
    @BindView(R.id.tvDetailTitle) TextView detailTitle;
    @BindView(R.id.tvRating) TextView rating;
    @BindView(R.id.tvGenre) TextView genre;
    @BindView(R.id.tvDescription) TextView description;
    MovieDetail detail = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int movieID = intent.getIntExtra("movieID",0);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/"+movieID+"?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US\n")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ResponseBody body = response.body();
                String resultString = body.string();
                try {
                    JSONObject jsonObject = new JSONObject(resultString);
                    detail = new MovieDetail(jsonObject);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setDetails();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        //Snackbar.make(findViewById(android.R.id.content),"movie id is"+ movieID, Snackbar.LENGTH_LONG).show();


    }
    public void setDetails(){
        rating.setText((detail.getRating())+"");
        detailTitle.setText(detail.getTitle());
        description.setText(detail.getDescription());
        genre.setText(detail.getGenre());
        Log.d(DetailsActivity.class.getName(),detail.getBackdropPath());
        Picasso.with(this).load(detail.getBackdropPath()).transform(new RoundedCornersTransformation(20, 20)).placeholder(this.getResources().getDrawable(R.drawable.placeholder_large)).into(posterImage);

    }
}
