package com.example.docchi.viewholders;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.R;
import com.example.docchi.adapters.PostImagesAdapter;
import com.example.docchi.adapters.PostPollAdapter;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class VotePollViewHolder extends RecyclerView.ViewHolder{
    private ImageView ivProfilePic;
    private TextView tvName;
    private TextView tvDescription;
    private RecyclerView rvPoll;

    private boolean showUserDetail;
    private Context context;
    private String loggedInUser;

    public VotePollViewHolder(@NonNull View itemView, boolean showUserDetails, Context context, String loggedInUser) {
        super(itemView);
        ivProfilePic = itemView.findViewById(R.id.ivProfilePicture);
        tvName = itemView.findViewById(R.id.tvName);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        rvPoll = itemView.findViewById(R.id.rvPolls);

        this.showUserDetail = showUserDetails;
        this.loggedInUser = loggedInUser;
        this.context = context;
    }

    public void bind(Post post){
        if (showUserDetail == false){
            tvName.setVisibility(View.GONE);
            ivProfilePic.setVisibility(View.GONE);
        }else{
            tvName.setText(post.getUser().getUsername());
            ParseFile file = post.getUser().getParseFile("profilePic");
            if(file!= null){
                Glide.with(context).load(file.getUrl()).transform(new CircleCrop()).into(ivProfilePic);
            }
            ivProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    ParseUser user = post.getUser();
                    Log.d("Posts Adapter", user.getUsername());
                    BottomNavigationView bnv = (BottomNavigationView) activity.findViewById(R.id.bottomNavigation);
                    bnv.setSelectedItemId(R.id.action_profile);
                    Fragment myFragment = new ProfileFragment(user);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, myFragment).addToBackStack(null).commit();
                }
            });
        }

        tvDescription.setText(post.getDescription());
        PostPollAdapter adapter = new PostPollAdapter(context, post, loggedInUser);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvPoll.setLayoutManager(HorizontalLayout);
        rvPoll.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}