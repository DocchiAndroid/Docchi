package com.example.docchi.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.R;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.model.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class HeaderViewHolder extends RecyclerView.ViewHolder{
    private ImageView ivProfilePic;
    private TextView tvUsername;
    private TextView tvName;
    private TextView tvNumPosts;

    private Context context;

    public HeaderViewHolder(@NonNull View itemView, Context context){
        super(itemView);
        ivProfilePic = itemView.findViewById(R.id.ivProfilePicture);
        tvName = itemView.findViewById(R.id.tvName);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        tvNumPosts = itemView.findViewById(R.id.tvPosts);
        this.context = context;
    }

    public void bind(Post post){
        ParseUser user = post.getUser();
        String fullname = user.getString("firstname") + " "
                + user.getString("lastname");
        tvName.setText(fullname);
        tvUsername.setText("@"+user.getUsername());
        int totalPosts = ProfileFragment.allPosts.size();
        String totalPost_String = totalPosts + (totalPosts==1?" Post":" Posts");
        tvNumPosts.setText(totalPost_String);
        ParseFile file = user.getParseFile("profilePic");
        if(file!= null){
            Glide.with(context).load(file.getUrl()).transform(new CircleCrop()).into(ivProfilePic);
        } else {
            Glide.with(context).load(R.drawable.default_pic).transform(new CircleCrop()).into(ivProfilePic);
        }

    }
}