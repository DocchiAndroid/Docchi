package com.example.docchi.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docchi.Post;
import com.example.docchi.R;

import java.util.ArrayList;
import java.util.List;


public class TimelineFragment extends Fragment {

  public static final String TAG = "PostFragment";
  private RecyclerView rvPosts;
  protected List<Post> allPosts;

  ActionBar actionBar;


  public TimelineFragment() {
    // Required empty public constructor
  }




  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_timeline, container, false);


  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    rvPosts = view.findViewById(R.id.rvPosts);
    allPosts = new ArrayList<>();

  }
}