package com.example.ruslan.udacity_second_project;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ruslan on 6/26/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    Context mcontext;
    String baseApiPoster ="http://image.tmdb.org/t/p/w185/";

    public MovieAdapter(Context context,List<Movie> movies) {
        super(context, 0,movies);
        mcontext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.movie_list,parent,false);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.movie_poster);
        TextView textView = (TextView)convertView.findViewById(R.id.movie_title);
        Picasso.with(mcontext).load(baseApiPoster+movie.getPoster_path()).into(imageView);
        textView.setText(movie.getTitle());
        return convertView;

    }
}
