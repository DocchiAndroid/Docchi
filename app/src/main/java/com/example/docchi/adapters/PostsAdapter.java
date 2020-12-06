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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.model.Post;
import com.example.docchi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private String loggedInUser;
    private boolean showUserDetail;

    public PostsAdapter(Context context, List<Post> posts, String loggedInUser, boolean showUserDetail){
        this.context = context;
        this.posts = posts;
        this.loggedInUser = loggedInUser;
        this.showUserDetail = showUserDetail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

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
    }
}
