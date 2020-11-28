package com.example.docchi.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.Image;
import com.example.docchi.Post;
import com.example.docchi.R;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
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
        ArrayList<Image> images;
        PostImagesAdapter adapter;
        LinearLayoutManager HorizontalLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePicture);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);

            rvImages = itemView.findViewById(R.id.rvPictureContainer);
            images = new ArrayList<>();
            adapter = new PostImagesAdapter(context, images);

            HorizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rvImages.setLayoutManager(HorizontalLayout);
            rvImages.setAdapter(adapter);
        }

        public void bind(Post post){
            tvDescription.setText(post.getDescription());
            tvName.setText(post.getUser().getUsername());
            images.addAll(post.getImages());
            adapter.notifyDataSetChanged();
            ParseFile file = post.getUser().getParseFile("profilePic");
            Glide.with(context).load(file.getUrl()).transform(new CircleCrop()).into(ivProfilePic);
        }
    }

}
