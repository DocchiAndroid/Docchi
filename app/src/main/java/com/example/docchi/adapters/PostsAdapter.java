package com.example.docchi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.model.Post;
import com.example.docchi.R;
import com.example.docchi.viewholders.HeaderViewHolder;
import com.example.docchi.viewholders.VoteImagesViewHolder;
import com.example.docchi.viewholders.VotePollViewHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private String loggedInUser;
    private boolean showHeader;
    public static final int VOTE_POLL = 0, VOTE_IMAGES = 1, HEADER = 2;

    public PostsAdapter(Context context, List<Post> posts, String loggedInUser, boolean showHeader) {
        this.context = context;
        this.posts = posts;
        this.loggedInUser = loggedInUser;
        this.showHeader = showHeader;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case VOTE_IMAGES:
                View v1 = inflater.inflate(R.layout.item_post_images, viewGroup, false);
                viewHolder = new VoteImagesViewHolder(v1, context, loggedInUser);
                break;
            case HEADER:
                View v2 = inflater.inflate(R.layout.fragment_profile_header, viewGroup, false);
                viewHolder = new HeaderViewHolder(v2, context);
                break;
            default:
                View v3 = inflater.inflate(R.layout.item_post_polls, viewGroup, false);
                viewHolder = new VotePollViewHolder(v3, context, loggedInUser);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(posts.size() == 0) return;

        Post post;
        if(showHeader){
            if(position == 0)
                post = posts.get(0);
            else
                post = posts.get(position-1);
        }
        else
            post = posts.get(position);

        switch (viewHolder.getItemViewType()) {
            case VOTE_IMAGES:
                VoteImagesViewHolder vh1 = (VoteImagesViewHolder) viewHolder;
                vh1.bind(post);
                break;
            case HEADER:
                HeaderViewHolder vh2 = (HeaderViewHolder) viewHolder;
                vh2.bind(post);
                break;
            default:
                VotePollViewHolder vh3 = (VotePollViewHolder) viewHolder;
                vh3.bind(post);
                break;
        }
    }


    @Override
    public int getItemCount() {
        if(showHeader)
            return posts.size() + 1;
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(showHeader) {
            if (position==0){
                return HEADER;
            }
            if (posts.get(position-1).getImages() != null) {
                return VOTE_IMAGES;
            } else if (posts.get(position-1).getPolls().isEmpty()) {
                return VOTE_POLL;
            }
            return -1;
        }
        if (posts.get(position).getImages() != null) {
            return VOTE_IMAGES;
        } else if (posts.get(position).getPolls().isEmpty()) {
            return VOTE_POLL;
        }
        return -1;
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

}


