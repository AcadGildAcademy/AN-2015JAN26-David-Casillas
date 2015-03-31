package com.acadgild.imdb;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieList extends ListFragment {

    private static final String TAG_RESULTS = "results";
    private static final String TAG_TITLE = "title";
    private static final String TAG_ID = "id";
    private static final String TAG_RELEASE_DATE = "release_date";
    private static final String TAG_POSTER_PATH = "poster_path";
    private static final String TAG_VOTE_AVERAGE = "vote_average";
    private static final String TAG_VOTE_COUNT = "vote_count";
    private static final String BASE_URL = "http://api.themoviedb.org/";
    private static final String API_VERSION = "3";
    private static final String API_KEY = "?api_key=8496be0b2149805afa458ab8ec27560c";
    public String CONTEXT_PATH;
    public String URL;

    private ListView listview;
    private ListAdapter adapter;
    private List<MovieInfo> movieList;
    public JSONArray results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.movie_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listview = getListView();
        movieList = new ArrayList<>();

        CONTEXT_PATH = "movie/now_playing";
        URL = BASE_URL + API_VERSION + "/" + CONTEXT_PATH + API_KEY;

        new GetMovieInfo().execute(URL);
        getActivity().setTitle("IMDb    Upcoming");

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        MovieInfo movieInfo = movieList.get(position);
        showDetails(movieInfo.getId());
    }

    void showDetails(String id) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MovieDetails movieDetailsFragment = new MovieDetails();
        Bundle arguments = new Bundle();
        arguments.putString("MovieID", id);
        movieDetailsFragment.setArguments(arguments);
        transaction.replace(R.id.fragmentContainer, movieDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {

            case R.id.watchlist:
                getActivity().setTitle("IMDb    Watchlist");

                return true;

            case R.id.favorites:
                getActivity().setTitle("IMDb    Favorites");

                return true;

            case R.id.refresh:

                return true;

            case R.id.most_popular:

                CONTEXT_PATH = "movie/popular";
                URL = BASE_URL + API_VERSION + "/" + CONTEXT_PATH + API_KEY;
                movieList.clear();
                new GetMovieInfo().execute(URL);
                getActivity().setTitle("IMDb    Most Popular");

                return true;

            case R.id.upcoming_movies:

                CONTEXT_PATH = "movie/upcoming";
                URL = BASE_URL + API_VERSION + "/" + CONTEXT_PATH + API_KEY;
                movieList.clear();
                new GetMovieInfo().execute(URL);
                getActivity().setTitle("IMDb    Upcoming");

                return true;

            case R.id.latest_movies:

                CONTEXT_PATH = "movie/latest";
                URL = BASE_URL + API_VERSION + "/" + CONTEXT_PATH + API_KEY;
                movieList.clear();
                new GetSingleMovieInfo().execute(URL);
                getActivity().setTitle("IMDb    Latest");

                return true;

            case R.id.now_playing:

                CONTEXT_PATH = "movie/now_playing";
                URL = BASE_URL + API_VERSION + "/" + CONTEXT_PATH + API_KEY;
                movieList.clear();
                new GetMovieInfo().execute(URL);
                getActivity().setTitle("IMDb    Now Playing");

                return true;

            case R.id.top_rated:

                CONTEXT_PATH = "movie/top_rated";
                URL = BASE_URL + API_VERSION + "/" + CONTEXT_PATH + API_KEY;
                movieList.clear();
                new GetMovieInfo().execute(URL);
                getActivity().setTitle("IMDb    Top Rated");

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetMovieInfo extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... urls) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    results = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject r = results.getJSONObject(i);
                        MovieInfo movieInfo = new MovieInfo();
                        movieInfo.setId(r.getString(TAG_ID));
                        movieInfo.setTitle(r.getString(TAG_TITLE));
                        movieInfo.setDate(r.getString(TAG_RELEASE_DATE));
                        movieInfo.setPoster(r.getString(TAG_POSTER_PATH));
                        movieInfo.setVote_average(r.getString(TAG_VOTE_AVERAGE));
                        movieInfo.setVote_count(r.getString(TAG_VOTE_COUNT));
                        movieList.add(movieInfo);
                    }
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                adapter = new ListAdapter(getActivity(), R.layout.list_item, movieList);
                listview.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class GetSingleMovieInfo extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... urls) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject o = new JSONObject(jsonStr);

                        MovieInfo movieInfo = new MovieInfo();
                        movieInfo.setId(o.getString(TAG_ID));
                        movieInfo.setTitle(o.getString(TAG_TITLE));
                        movieInfo.setDate(o.getString(TAG_RELEASE_DATE));
                        movieInfo.setPoster(o.getString(TAG_POSTER_PATH));
                        movieInfo.setVote_average(o.getString(TAG_VOTE_AVERAGE));
                        movieInfo.setVote_count(o.getString(TAG_VOTE_COUNT));
                        movieList.add(movieInfo);

                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                adapter = new ListAdapter(getActivity(), R.layout.list_item, movieList);
                listview.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            }
        }
    }
}
