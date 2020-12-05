package com.example.docchi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docchi.MainActivity;
import com.example.docchi.R;
import com.example.docchi.adapters.UsersAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    private RecyclerView rvUsers;
    protected UsersAdapter adapter;
    protected List<ParseUser> allUsers;

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
                allUsers.addAll(userObjects);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvUsers = view.findViewById(R.id.rvUsers);
        allUsers = new ArrayList<>();

        adapter = new UsersAdapter(getContext(), allUsers);

        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        queryUsers();
    }
}