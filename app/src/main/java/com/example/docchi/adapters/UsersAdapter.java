package com.example.docchi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<ParseUser> users;
    private List<ParseUser> allUsers;

    public UsersAdapter(Context context, List<ParseUser> users){
        this.context = context;
        this.users = users;
        this.allUsers = users;
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
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        users.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<ParseUser> list) {
        users.addAll(list);
        notifyDataSetChanged();
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<ParseUser> usersFiltered = new ArrayList<>();
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    usersFiltered = allUsers;
                } else {
                    List<ParseUser> filteredList = new ArrayList<>();
                    for (ParseUser row : allUsers) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getUsername().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                            Log.d("UsersAdapter", row.getUsername());
                        }
                    }
                    usersFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = usersFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                users = (ArrayList<ParseUser>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface UsersAdapterListener {
        void onUserSelected(ParseUser user);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfilePic;
        private TextView tvUsername;
        private TextView tvName;
        private View itemView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePicture);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvName = itemView.findViewById(R.id.tvName);
            this.itemView = itemView;
        }

        public void bind(ParseUser user){
            tvUsername.setText(user.getUsername());
            String fullname = user.getString("firstname") + " " + user.getString("lastname");
            tvName.setText(fullname);
            ParseFile file = user.getParseFile("profilePic");
            if(file!= null){
                Glide.with(context).load(file.getUrl()).transform(new CircleCrop()).into(ivProfilePic);
            } else {
                Glide.with(context).load(R.drawable.default_pic).transform(new CircleCrop()).into(ivProfilePic);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    BottomNavigationView bnv = (BottomNavigationView) activity.findViewById(R.id.bottomNavigation);
                    bnv.setSelectedItemId(R.id.action_profile);
                    Fragment myFragment = new ProfileFragment(user);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPager, myFragment).addToBackStack(null).commit();
                }
            });
        }
    }
}
