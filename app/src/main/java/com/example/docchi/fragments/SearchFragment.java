package com.example.docchi.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docchi.AboutActivity;
import com.example.docchi.HelpActivity;
import com.example.docchi.LoginActivity;
import com.example.docchi.MainActivity;
import com.example.docchi.R;
import com.example.docchi.SettingActivity;
import com.example.docchi.adapters.UsersAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;


public class SearchFragment extends Fragment implements UsersAdapter.UsersAdapterListener {

    public static final String TAG = "SearchFragment";
    private RecyclerView rvUsers;
    protected UsersAdapter adapter;
    protected List<ParseUser> allUsers;
    private SearchView searchView;
    private TextView tvUsername;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ActionBar actionBar = ((MainActivity) getContext()).getSupportActionBar();
        actionBar.setTitle("Search");
        setHasOptionsMenu(true);
        return v;
    }

    protected void queryUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> userObjects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting users", e);
                    return;
                }
                for (ParseUser user : userObjects) {
                    Log.i(TAG, "User: " + user.getUsername());
                }
                allUsers.clear();
                allUsers.addAll(userObjects);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.About:
                Intent intent1 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent1);
                return true;
            case R.id.Help:
                Intent intent2 = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent2);
                return true;
            case R.id.Settings:
                Intent intent3 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent3);
                return true;
            case R.id.Logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        ((MainActivity) getActivity()).finish();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvUsers = view.findViewById(R.id.rvUsers);
        allUsers = new ArrayList<>();

        adapter = new UsersAdapter(getContext(), allUsers);

        tvUsername = view.findViewById(R.id.tvUsername);
        rvUsers.setAdapter(adapter);



        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        queryUsers();
    }

    @Override
    public void onUserSelected(ParseUser user) {
        Toast.makeText(getApplicationContext(), "Selected: " + user.getUsername(), Toast.LENGTH_SHORT).show();

    }
}