package com.example.docchi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.docchi.AboutActivity;
import com.example.docchi.HelpActivity;
import com.example.docchi.LoginActivity;
import com.example.docchi.MainActivity;
import com.example.docchi.Post;
import com.example.docchi.R;
import com.example.docchi.SettingActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

  public static final String TAG = "ProfileFragment";
  protected com.example.docchi.fragments.PostsAdapter adapter;
  protected List<Post> allPosts;
  private String username;
  private ImageView ivProfilePic;
  private TextView tvName;


  public ProfileFragment(String username) {
    // Required empty public constructor
    this.username = username;
    Log.d(TAG, "ProfileFragment: " + this.username);
  }


  public ProfileFragment() {
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_top, menu);
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
        //intent3.putExtra("LoggedInUser", ParseUser.getCurrentUser().getUsername());
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
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);

    //Tool bar title
    ActionBar actionBar = ((MainActivity) getContext()).getSupportActionBar();
    actionBar.setTitle("Docchi");


  }

  //&oncreaview for the profile
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {


    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_profile, container, false);
//    ActionBar actionBar = ((MainActivity) getContext()).getSupportActionBar();
//    actionBar.setTitle("Docchi");

    return v;



  }


//  public ProfileFragment(String username) {
//    super(username);
//  }


  protected void queryPost() {
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(Post.KEY_USER);
    query.include(Post.KEY_IMAGES);
    query.setLimit(20);
    query.addDescendingOrder(Post.KEY_CREATED_KEY);
    query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());

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

  //&Added onViewCreated
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RecyclerView rvPosts = view.findViewById(R.id.rvPosts);
    ivProfilePic = view.findViewById(R.id.ivProfilePicture);
    tvName = view.findViewById(R.id.tvName);
    loadImage();



    allPosts = new ArrayList<>();

    adapter = new com.example.docchi.fragments.PostsAdapter(getContext(), allPosts, username, false);

    rvPosts.setAdapter(adapter);
    rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
    queryPost();
  }

  public void loadImage() {
    ParseUser user = ParseUser.getCurrentUser();
    tvName.setText(user.getUsername());
  ParseFile file = user.getParseFile("profilePic");
      Glide.with(this)
              .load(file.getUrl())
              .into(ivProfilePic);
    }
  }

