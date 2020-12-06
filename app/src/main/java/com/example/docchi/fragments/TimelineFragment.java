package com.example.docchi.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docchi.MainActivity;
import com.example.docchi.model.Post;
import com.example.docchi.R;
import com.example.docchi.adapters.PostsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TimelineFragment extends Fragment {

  public static final String TAG = "TimelineFragment";
  private RecyclerView rvPosts;
  protected PostsAdapter adapter;
  protected List<Post> allPosts;
  private String username;

  public TimelineFragment(String username) {
    // Required empty public constructor
    this.username = username;
    Log.d(TAG, "TimelineFragment: " + this.username);
  }

  public TimelineFragment() {

  }

    @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {



    // Inflate the layout for this fragment


     View v = inflater.inflate(R.layout.fragment_timeline, container, false);
      ActionBar actionBar = ((MainActivity) getContext()).getSupportActionBar();
      actionBar.setTitle("Docchi");
      return v;
  }

//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                           Bundle savedInstanceState) {
//    // Inflate the layout for this fragment
//    return inflater.inflate(R.layout.fragment_timeline, container, false);
//
//  }

  protected void queryPost() {
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(Post.KEY_USER);
    query.include(Post.KEY_IMAGES);
    query.setLimit(20);
    query.addDescendingOrder(Post.KEY_CREATED_KEY);

    query.findInBackground(new FindCallback<Post>() {
      @Override
      public void done(List<Post> posts, ParseException e) {
        if (e != null) {
          Log.e(TAG, "Issue with getting posts", e);
          return;
        }
        for (Post post : posts) {
          Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
        }
        allPosts.addAll(posts);
        adapter.notifyDataSetChanged();
      }
    });

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);



    rvPosts = view.findViewById(R.id.rvPosts);
    allPosts = new ArrayList<>();

    adapter = new PostsAdapter(getContext(), allPosts, username, true);

    rvPosts.setAdapter(adapter);
    rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
    queryPost();

    //Timestamp
//    Calendar calendar = Calendar.getInstance();
//    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

//    TextView textViewDate = view.findViewById(R.id.text_view_date);
//    textViewDate.setText(currentDate);
  }
}