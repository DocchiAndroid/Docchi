package com.example.docchi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.docchi.model.Image;
import com.example.docchi.model.Post;
import com.example.docchi.R;
import com.parse.ParseFile;

import java.util.ArrayList;

import static com.parse.Parse.getApplicationContext;

public class PostImagesAdapter extends RecyclerView.Adapter<PostImagesAdapter.MyView> {

    private Context context;
    private ArrayList<Image> images;
    private String username;
    private Post post;


    public PostImagesAdapter(Context context, Post post, String username){
        this.context = context;
        this.images = post.getImages();
        this.username = username;
        this.post = post;
    }

    @NonNull
    @Override
    public PostImagesAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_images_image, parent, false);
        return new PostImagesAdapter.MyView(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PostImagesAdapter.MyView holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvCount;
        ImageView vtnButton;


        public MyView(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvCount = itemView.findViewById(R.id.tvCount);
            vtnButton = itemView.findViewById(R.id.vtnButton);
        }

        public void bind(int position){
            Image image = images.get(position);
            //Bind the post data to the view elements
            tvCount.setText(Integer.toString(image.getCount()));
            //Using Glide library for image
            ParseFile imageFile = image.getImageUrl();
            if(imageFile != null) {
                Log.d("Bind Post Image", imageFile.getUrl());
                Glide.with(context).load(imageFile.getUrl()).into(ivImage);
            }


            vtnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = post.previousVoteImages(username);
                    if(pos != -1 && pos != position){
                        Toast.makeText(context, "You have already voted " + pos, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    image.changeVote(username);
                    images.set(position, image);
                    post.updateImages(images);
                    //update ui
                    tvCount.setText(Integer.toString(image.getCount()));

                    //shake animation on vote_btn
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    vtnButton.startAnimation(shake);
                }
            });

        }
    }
}
