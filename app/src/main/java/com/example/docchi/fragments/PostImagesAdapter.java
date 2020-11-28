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

import com.bumptech.glide.Glide;
import com.example.docchi.Image;
import com.example.docchi.R;
import com.parse.ParseFile;

import java.util.List;

public class PostImagesAdapter extends RecyclerView.Adapter<PostImagesAdapter.MyView> {

    private Context context;
    private List<Image> images;

    public PostImagesAdapter(Context context, List<Image> images){
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public PostImagesAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_post, parent, false);
        return new PostImagesAdapter.MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostImagesAdapter.MyView holder, int position) {
        Image image = images.get(position);
        holder.bind(image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class MyView extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageView voteImage;
        TextView tvCount;

        public MyView(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            voteImage = itemView.findViewById(R.id.ivVote);
            tvCount = itemView.findViewById(R.id.tvCount);
            voteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        public void bind(Image image){
            //Bind the post data to the view elements
            tvCount.setText(Integer.toString(image.getCount()));
            //Using Glide library for image
            ParseFile imageFile = image.getImageUrl();
            if(imageFile != null) {
                Log.d("Bind Post Image", imageFile.getUrl());
                Glide.with(context).load(imageFile.getUrl()).into(ivImage);
            }
        }
    }
}
