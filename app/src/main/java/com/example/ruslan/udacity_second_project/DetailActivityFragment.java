package com.example.ruslan.udacity_second_project;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        final String EXTRA_VALUE = "EXTRA";
        String baseApiPoster ="http://image.tmdb.org/t/p/w185/";
       Movie movie =(Movie)getActivity().getIntent().getExtras().getSerializable(EXTRA_VALUE);
        if (movie != null) {
            TextView text_date = (TextView) view.findViewById(R.id.text_year);
            text_date.setText(movie.getRelease_date());
            TextView text_vote = (TextView) view.findViewById(R.id.text_vote);
            text_vote.setText(movie.getVote_average()+"/10");
            TextView text_title = (TextView) view.findViewById(R.id.text_title);
            text_title.setText(movie.getTitle());
            TextView text_overview = (TextView) view.findViewById(R.id.text_overview);
            text_overview.setText(movie.getOverview());
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Picasso.with(getActivity()).load(baseApiPoster+movie.getPoster_path()).into(imageView);
        }
        return view;
    }
}
