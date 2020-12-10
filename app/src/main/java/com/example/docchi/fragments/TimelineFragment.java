package com.example.docchi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docchi.AboutActivity;
import com.example.docchi.HelpActivity;
import com.example.docchi.LoginActivity;
import com.example.docchi.MainActivity;
import com.example.docchi.R;
import com.example.docchi.SettingActivity;
import com.example.docchi.adapters.PostsAdapter;
import com.example.docchi.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class TimelineFragment extends Fragment {

  public static final String TAG = "TimelineFragment";
  public static final int VERTICAL_ITEM_SPACE = 20;
  private RecyclerView rvPosts;
  public static PostsAdapter adapter;
  protected List<Post> allPosts;
  private String username;

  private static final int TYPE_HEADER = 0;
  private static final int TYPE_ITEM = 1;

  public TimelineFragment(String username) {
    // Required empty public constructor
    this.username = username;
    Log.d(TAG, "TimelineFragment: " + this.username);
  }

  public TimelineFragment() {

  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

//      Calendar calendar = Calendar.getInstance();
//      String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//      TextView textViewDate = View.findViewById(R.id.text_view_date);
//      textViewDate.setText(currentDate);

    // Inflate the layout for this fragment
      View v = inflater.inflate(R.layout.fragment_timeline, container, false);
      setHasOptionsMenu(true);
      ActionBar actionBar = ((MainActivity) getContext()).getSupportActionBar();
      actionBar.setTitle("Docchi");
      return v;
  }

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

    adapter = new PostsAdapter(getContext(), allPosts, username, false);

    rvPosts.setAdapter(adapter);
    rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));


    //Divider for recyclerview
//    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//    DividerItemDecoration itemDecorator  = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
//    itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border));
//
//    rvPosts.setHasFixedSize(true);
//    rvPosts.setLayoutManager(layoutManager);
//    rvPosts.addItemDecoration(itemDecorator);
//    rvPosts.addItemDecoration(new SpacesItemDecoration(VERTICAL_ITEM_SPACE));

    if(allPosts.size() == 0) queryPost();


  }
}