package com.example.docchi.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.docchi.Post;
import com.example.docchi.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class ProfileFragment extends TimelineFragment {

  public static final String TAG = "TimelineFragment";
  public Button btnLogOut;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflateFragment(inflater, container);


    return view;
  }


  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_top, menu);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  public ProfileFragment(String username) {
    super(username);
  }

  public ProfileFragment() {

  }




  @Override
  protected void queryPost() {
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(Post.KEY_USER);
    query.include(Post.KEY_IMAGES);

    query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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
}
