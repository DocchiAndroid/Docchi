package com.example.docchi.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context context;
    private List<ParseUser> users;

    public UsersAdapter(Context context, List<ParseUser> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParseUser user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfilePic;
        private TextView tvName;
        private View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePicture);
            tvName = itemView.findViewById(R.id.tvUsername);
            this.itemView = itemView;
        }

        public void bind(ParseUser user){
            tvName.setText(user.getUsername());
            ParseFile file = user.getParseFile("profilePic");
            if(file!= null){
                Glide.with(context).load(file.getUrl()).transform(new CircleCrop()).into(ivProfilePic);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    BottomNavigationView bnv = (BottomNavigationView) activity.findViewById(R.id.bottomNavigation);
                    bnv.setSelectedItemId(R.id.action_profile);
                    Fragment myFragment = new ProfileFragment(user);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, myFragment).addToBackStack(null).commit();
                }
            });
        }
    }
}
