package com.example.docchi.viewholders;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.R;
import com.example.docchi.adapters.PostImagesAdapter;
import com.example.docchi.adapters.PostPollAdapter;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.fragments.ViewPostDialogFragment;
import com.example.docchi.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class VotePollViewHolder extends RecyclerView.ViewHolder{
    private ImageView ivProfilePic;
    private TextView tvName;
    private TextView tvDescription;
    private RecyclerView rvPoll;
    private TextView tvMoreDetails;
    private TextView tvDate;

    private Context context;
    private String loggedInUser;

    public VotePollViewHolder(@NonNull View itemView, Context context, String loggedInUser) {
        super(itemView);
        ivProfilePic = itemView.findViewById(R.id.ivProfilePicture);
        tvName = itemView.findViewById(R.id.tvName);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        rvPoll = itemView.findViewById(R.id.rvPolls);
        tvMoreDetails = itemView.findViewById(R.id.MoreDetails);
        tvDate = itemView.findViewById(R.id.text_view_date);

        this.loggedInUser = loggedInUser;
        this.context = context;
    }

    public void bind(Post post){
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

        tvMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPostDetails(post);
            }
        });

        tvDate.setText(post.getTimeDifference());
        tvDescription.setText(post.getDescription());
        setVotesComments(post);
        PostPollAdapter adapter = new PostPollAdapter(context, post, loggedInUser);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvPoll.setLayoutManager(HorizontalLayout);
        rvPoll.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setVotesComments(Post post){
        int totalVotes = post.getTotalPollVotes();
        int totalComments = post.getTotalComments();
        String moreDetails = totalVotes + (totalVotes==1?" VOTE":" VOTES") + "   " + totalComments + (totalComments==1?" COMMENT":" COMMENTS");
        tvMoreDetails.setText(moreDetails);
    }

    private void showPostDetails(Post post) {
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        FragmentManager fm = activity.getSupportFragmentManager();
        ViewPostDialogFragment showPostDetailsDialog  = ViewPostDialogFragment.newInstance(post);
        showPostDetailsDialog.show(fm, "fragment_view_post");
    }
}