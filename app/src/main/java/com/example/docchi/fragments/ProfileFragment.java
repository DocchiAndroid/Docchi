package com.example.docchi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docchi.LoginActivity;
import com.example.docchi.Post;
import com.example.docchi.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class ProfileFragment extends TimelineFragment {

  public Button btnLogOut;

  public ProfileFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_profile, container, false);

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
          Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername()
                  + " " + post.getImages().get(0).getImageUrl().getUrl() + " " + post.getImages().get(0).getCount());
        }
        allPosts.addAll(posts);
        adapter.notifyDataSetChanged();
      }
    });
  }

}

//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                           Bundle savedInstanceState) {
//    // Inflate the layout for this fragment
//    return inflater.inflate(R.layout.fragment_profile, container, false);
//  }
//
//  @Override
//  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//    super.onViewCreated(view, savedInstanceState);
//    btnLogOut = view.findViewById(R.id.btnLogout);
//    btnLogOut.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        ParseUser.logOut();
//        checkLogin();
//        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
//      }
//
//      });
//  }

//    private void checkLogin() {
//       if(ParseUser.getCurrentUser() == null){
//            Intent i = new Intent(getActivity(), LoginActivity.class);
//            startActivity(i);
//        }
//    }




