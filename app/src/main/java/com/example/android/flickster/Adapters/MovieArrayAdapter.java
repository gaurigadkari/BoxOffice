package com.example.android.flickster.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.flickster.DetailsActivity;
import com.example.android.flickster.MovieActivity;
import com.example.android.flickster.R;
import com.example.android.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.R.attr.resource;
import static android.R.attr.type;
import static android.R.attr.value;
import static com.example.android.flickster.R.id.ivMovieImage;
import static com.example.android.flickster.R.id.tvOverview;
import static com.example.android.flickster.R.id.tvTitle;



public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_POPULAR = 1;
    private static final int TYPE_MAX_COUNT = 2;
    private LayoutInflater mInflater;

    static class ViewHolder{
        @BindView(R.id.ivMovieImage) ImageView posterImage;
        @BindView(R.id.tvTitle) TextView title;
        @BindView(R.id.tvOverview) TextView overview;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class ViewHolderPopular{
        @BindView(R.id.ivPopularMovieImage) ImageView popularPosterImage;
        public ViewHolderPopular(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Movie movie = getItem(position);
        //boolean popular = movie.isPopular();
        ViewHolder viewHolder = null;
        ViewHolderPopular viewHolderPopular = null;
        getItemViewType(position);
        switch (getItemViewType(position)){
            case TYPE_ITEM:
            if(convertView == null) {
                convertView = mInflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

                viewHolder.title.setText(movie.getOriginalTitle());
                viewHolder.overview.setText(movie.getOverview());


                int orientation = getContext().getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext()).load(movie.getPosterPath()).transform(new RoundedCornersTransformation(20, 20)).placeholder(getContext().getResources().getDrawable(R.drawable.placeholder_large)).into(viewHolder.posterImage);
                }
                else {
                    Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(20, 20)).placeholder(getContext().getResources().getDrawable(R.drawable.placeholder_large)).into(viewHolder.posterImage);
                }
                break;
            case TYPE_POPULAR:
                if(convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_movie_popular, parent, false);
                    viewHolderPopular = new ViewHolderPopular(convertView);
                    convertView.setTag(viewHolderPopular);
                }
                else {
                    viewHolderPopular = (ViewHolderPopular) convertView.getTag();
                }
                Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(20, 20)).placeholder(getContext().getResources().getDrawable(R.drawable.placeholder_large)).into(viewHolderPopular.popularPosterImage);


        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieActivity movieActivity = (MovieActivity) getContext();
                Intent myIntent = new Intent(movieActivity, DetailsActivity.class);
                myIntent.putExtra("movieID", movie.getMovieID()); //Optional parameters
                movieActivity.startActivity(myIntent);
            }
        });
        /*if(convertView == null){
            switch (type) {
                case TYPE_ITEM:
                    //LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = mInflater.inflate(R.layout.item_movie, parent, false);
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                    break;
                case TYPE_POPULAR:
                    convertView = mInflater.inflate(R.layout.item_movie_popular, parent, false);
                    convertView.setTag(viewHolderPopular);
                    break;
            }

        }

        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        //clear the image from convertView
        //ivImage.setImageResource(0);

        //TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        //TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());


        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(20, 20)).placeholder(getContext().getResources().getDrawable(R.drawable.placeholder_large)).into(viewHolder.posterImage);
        }
        else {
            Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(20, 20)).placeholder(getContext().getResources().getDrawable(R.drawable.placeholder_large)).into(viewHolder.posterImage);
        }*/
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        //super.getViewTypeCount();
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = getItem(position);
        if(movie.isPopular()) {
            return TYPE_POPULAR;
        }
        else {
            return TYPE_ITEM;
        }
    }

}
