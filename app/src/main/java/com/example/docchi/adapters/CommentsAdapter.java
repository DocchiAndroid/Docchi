package com.example.docchi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.model.Comment;
import com.example.docchi.R;
import com.parse.ParseFile;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context context;
    private List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments){
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfilePic;
        private TextView tvName;
        private TextView tvDateTime;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.profilePic);
            tvName = itemView.findViewById(R.id.name);
            tvDateTime = itemView.findViewById(R.id.time);
            tvDescription = itemView.findViewById(R.id.content);
        }

        public void bind(Comment comment){
            tvName.setText(comment.getParseUser().getUsername());
            ParseFile file = comment.getParseUser().getParseFile("profilePic");
            if(file!= null){
                Glide.with(context).load(file.getUrl()).transform(new CircleCrop()).into(ivProfilePic);
            }
            tvDescription.setText(comment.getDescription());
            tvDateTime.setText(comment.getTimeDifference());
        }
    }
}
