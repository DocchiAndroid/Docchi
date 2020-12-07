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
    private boolean showUserDetail;
    private final int VOTE_POLL = 0, VOTE_IMAGES = 1;

    public PostsAdapter(Context context, List<Post> posts, String loggedInUser, boolean showUserDetail){
        this.context = context;
        this.posts = posts;
        this.loggedInUser = loggedInUser;
        this.showUserDetail = showUserDetail;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case VOTE_IMAGES:
                View v1 = inflater.inflate(R.layout.item_post_images, viewGroup, false);
                viewHolder = new VoteImagesViewHolder(v1, showUserDetail, context, loggedInUser);
                break;
            default:
                View v = inflater.inflate(R.layout.item_post_polls, viewGroup, false);
                viewHolder = new VotePollViewHolder(v, showUserDetail, context, loggedInUser);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Post post = posts.get(position);
        switch (viewHolder.getItemViewType()) {
            case VOTE_IMAGES:
                VoteImagesViewHolder vh1 = (VoteImagesViewHolder) viewHolder;
                vh1.bind(post);
                break;
            default:
                VotePollViewHolder vh = (VotePollViewHolder) viewHolder;
                vh.bind(post);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (posts.get(position).getImages() != null) {
            return VOTE_IMAGES;
        } else if (posts.get(position).getPolls().isEmpty()) {
            return VOTE_POLL;
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivProfilePic;
        private TextView tvName;
        private TextView tvDescription;
        private RecyclerView rvImages;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePicture);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            rvImages = itemView.findViewById(R.id.rvPictureContainer);
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
            PostImagesAdapter adapter = new PostImagesAdapter(context, post, loggedInUser);
            LinearLayoutManager HorizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);



            rvImages.setLayoutManager(HorizontalLayout);
            rvImages.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }
        return -1;
    }
}
