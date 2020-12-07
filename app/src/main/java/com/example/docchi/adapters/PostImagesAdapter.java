package com.example.docchi.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.bumptech.glide.Glide;
import com.example.docchi.model.Image;
import com.example.docchi.model.Post;
import com.example.docchi.R;
import com.parse.ParseFile;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;

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
        ImageView voteImage;
        TextView tvCount;
        TextView btnVote;
        AnimatedVectorDrawableCompat avd;
        AnimatedVectorDrawable avd2;

        public MyView(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            voteImage = itemView.findViewById(R.id.ivVote);
            btnVote = itemView.findViewById(R.id.btnVote);
            tvCount = itemView.findViewById(R.id.tvCount);


            ((Activity) itemView.getContext()).getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

            );
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


            btnVote.setOnClickListener(new View.OnClickListener() {
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

                    Drawable drawable = voteImage.getDrawable();
                    if(drawable instanceof AnimatedVectorDrawableCompat){
                        avd = (AnimatedVectorDrawableCompat) drawable;
                        avd.start();
                    }else if(drawable instanceof AnimatedVectorDrawable)
                        avd2 = (AnimatedVectorDrawable) drawable;
                    avd2.start();
                }
            });

        }
    }
}
