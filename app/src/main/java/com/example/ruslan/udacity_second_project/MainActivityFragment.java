package com.example.ruslan.udacity_second_project;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 6/26/16.
 */
public class MainActivityFragment extends Fragment {

     GridView gridView;
    public final static String EXTRA_VALUE = "EXTRA";
    MovieAdapter movieAdapter;
    List<Movie> list_movie = new ArrayList<>();
    String flags = "popular";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public MainActivityFragment() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_top_rated) {
            updateView("top_rated");
            flags = "top_rated";
            return true;
        }
        if (id == R.id.action_popular){
            updateView("popular");
            flags = "popular";
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView(flags);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView(flags);
        System.out.println(flags);
    }

    public void updateView(String parametr) {
        FetchTask fetch = new FetchTask();
        fetch.execute(parametr);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View converView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView)converView.findViewById(R.id.movie_grid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                Bundle args = new Bundle();
                args.putSerializable(EXTRA_VALUE,list_movie.get(position));
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        return converView;
    }


    private class FetchTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            movieAdapter = new MovieAdapter(getActivity(),movies);
            gridView.setAdapter(movieAdapter);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {

            String baseApi = "http://api.themoviedb.org/3/movie/";
            String APPID = "?api_key=e6cd104d126520f7ba3b64baf367affc";
            String forecaseJson = null;
            BufferedReader reader = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(baseApi+params[0]+APPID);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                if (sb.length() == 0)
                    return null;
                forecaseJson = sb.toString();
                Log.e("TAG", forecaseJson);


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivityFragment", "Error closing stream", e);
                    }
                }
            }
            List<Movie> list = null;
            try
            {
                 list = getMovieDataFromJson(forecaseJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list_movie = list;
            return list;
        }
        private List<Movie> getMovieDataFromJson(String forecastJsonStr)
                throws JSONException {

            final String MOVIE_LISTS = "results";
            List<Movie> list = new ArrayList<>();
            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray movieArray = forecastJson.getJSONArray(MOVIE_LISTS);
            for (int i = 0; i <movieArray.length() ; i++) {
                JSONObject object = movieArray.getJSONObject(i);
                list.add(new Movie(
                        object.getString("title"),
                        object.getString("overview"),
                        object.getString("poster_path"),
                        object.getString("release_date"),
                        object.getString("vote_average")));
            }
            return list;
        }
    }
}
