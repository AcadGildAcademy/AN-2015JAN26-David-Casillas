package com.acadgild.imdb.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.acadgild.imdb.async.GetMovieInfo;
import com.acadgild.imdb.async.GetSingleMovieInfo;
import com.acadgild.imdb.model.Constants;
import com.acadgild.imdb.model.MovieInfo;
import com.acadgild.imdb.R;

import java.util.ArrayList;
import java.util.List;

public class MovieList extends ListFragment {

    public String CONTEXT_PATH;
    public String URL;
    private ListView listview;
    private List<MovieInfo> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setRetainInstance(true);
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.movie_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listview = getListView();
        movieList = new ArrayList<>();

        CONTEXT_PATH = "movie/now_playing";

        SetList(CONTEXT_PATH);

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
                SetList(CONTEXT_PATH);
                getActivity().setTitle("IMDb    Most Popular");

                return true;

            case R.id.upcoming_movies:

                CONTEXT_PATH = "movie/upcoming";
                SetList(CONTEXT_PATH);
                getActivity().setTitle("IMDb    Upcoming");

                return true;

            case R.id.latest_movies:

                CONTEXT_PATH = "movie/latest";
                SetListSingleMovie(CONTEXT_PATH);
                getActivity().setTitle("IMDb    Latest");

                return true;

            case R.id.now_playing:

                CONTEXT_PATH = "movie/now_playing";
                SetList(CONTEXT_PATH);
                getActivity().setTitle("IMDb    Now Playing");

                return true;

            case R.id.top_rated:

                CONTEXT_PATH = "movie/top_rated";
                SetList(CONTEXT_PATH);
                getActivity().setTitle("IMDb    Top Rated");

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void SetList(String context_path) {

        URL = Constants.BASE_URL + Constants.API_VERSION + "/" + context_path + Constants.API_KEY;
        movieList.clear();
        new GetMovieInfo(getActivity(), movieList, listview).execute(URL);
    }

    private void SetListSingleMovie(String context_path) {

        URL = Constants.BASE_URL + Constants.API_VERSION + "/" + context_path + Constants.API_KEY;
        movieList.clear();
        new GetSingleMovieInfo(getActivity(), movieList, listview).execute(URL);
    }
}
