package com.example.docchi.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.docchi.model.Image;
import com.example.docchi.model.Poll;
import com.example.docchi.model.Post;
import com.example.docchi.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.util.ArrayList;

import static com.parse.Parse.getApplicationContext;

public class PostPollAdapter extends RecyclerView.Adapter<PostPollAdapter.MyView> {

    private Context context;
    private ArrayList<Poll> polls;
    private String username;
    private Post post;

    public PostPollAdapter(Context context, Post post, String username){
        this.context = context;
        this.polls = post.getPolls();
        this.username = username;
        this.post = post;
    }

    @NonNull
    @Override
    public PostPollAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_polls_poll, parent, false);
        return new MyView(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView tvDescription;
        ImageView voteImage;
        TextView tvCount;


        public MyView(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            voteImage = itemView.findViewById(R.id.vote);
            tvCount = itemView.findViewById(R.id.voteCount);
        }

        @SuppressLint("SetTextI18n")
        public void bind(int position){
            Poll poll = polls.get(position);
            //Bind the post data to the view elements
            tvCount.setText(Integer.toString(poll.getVotes()));
            tvDescription.setText(poll.getItemDescription());

            voteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = post.previousVotePoll(username);
                    if(pos != -1 && pos != position){
                        Toast.makeText(context, "You have already voted " + pos, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    poll.changeVote(username);
                    polls.set(position, poll);
                    post.setPoll(polls);
                    //update ui
                    tvCount.setText(Integer.toString(poll.getVotes()));
                }
            });
        }
    }
}