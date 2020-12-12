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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.docchi.AboutActivity;
import com.example.docchi.HelpActivity;
import com.example.docchi.LoginActivity;
import com.example.docchi.MainActivity;
import com.example.docchi.model.Post;
import com.example.docchi.R;
import com.example.docchi.SettingActivity;
import com.example.docchi.adapters.PostsAdapter;
import com.example.docchi.model.SpacesItemDecoration;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

  public static final String TAG = "ProfileFragment";
  protected PostsAdapter adapter;
  public static List<Post> allPosts;
  private ParseUser user;
  private ImageView ivProfilePic;
  private TextView tvName;
  protected Toolbar toolbar;
  public SwipeRefreshLayout swipeContainer;

  public ProfileFragment(ParseUser user) {
    this.user = user;
    Log.d(TAG, "ProfileFragment: " + this.user.getUsername());
  }


  public ProfileFragment() {
    this.user = ParseUser.getCurrentUser();
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
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);



  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_profile, container, false);
    return v;
  }

  protected void queryPost() {
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(Post.KEY_USER);
    query.include(Post.KEY_IMAGES);
    query.setLimit(20);
    query.addDescendingOrder(Post.KEY_CREATED_KEY);
    query.whereEqualTo(Post.KEY_USER, user);

    query.findInBackground(new FindCallback<Post>() {
      @Override
      public void done(List<Post> posts, ParseException e) {
        if (e != null) {
          Log.e(TAG, "Issue with getting posts", e);
          return;
        }
        adapter.clear();
        adapter.addAll(posts);
        adapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);
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

    allPosts = new ArrayList<>();
    adapter = new PostsAdapter(getContext(), allPosts, user.getUsername(), true);

    rvPosts.setAdapter(adapter);
    rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
    queryPost();

    swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
    // Setup refresh listener which triggers new data loading
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        queryPost();
      }
    });
    // Configure the refreshing colors
    swipeContainer.setColorSchemeResources(R.color.blue,
            R.color.teal_700,
            R.color.secondary_color);
    swipeContainer.setRefreshing(false);
  }
}

