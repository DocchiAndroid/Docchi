package com.example.docchi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.docchi.R;
import com.example.docchi.fragments.CreatePostFragment;
import com.example.docchi.viewholders.VoteImagesViewHolder;

import java.io.File;
import java.util.List;

public class CreatePostAdapter extends RecyclerView.Adapter<CreatePostAdapter.CreatePostImageViewHolder> {

    private Context context;
    private List<File> images;

    public CreatePostAdapter(Context context, List<File> images){
        this.context=context;
        this.images=images;
    }


    @NonNull
    @Override
    public CreatePostImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.post_image_layout, parent, false);
        return new CreatePostImageViewHolder(v1);

    }

    @Override
    public void onBindViewHolder(@NonNull CreatePostImageViewHolder holder, int position) {
        holder.bind(images.get(position));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class CreatePostImageViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;

        public CreatePostImageViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.postImageLayout);
        }

        public void bind(File file){
            Glide.with(context).load(file).into(image);
        }


    }
}
