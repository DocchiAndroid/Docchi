package com.example.docchi.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.R;
import com.example.docchi.adapters.CommentsAdapter;
import com.example.docchi.adapters.PostImagesAdapter;
import com.example.docchi.adapters.UsersAdapter;
import com.example.docchi.model.Comment;
import com.example.docchi.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewPostDialogFragment extends DialogFragment {

    private ImageView ivProfilePic;
    private TextView tvName;
    private TextView tvDescription;
    private RecyclerView rvImages;
    private RecyclerView rvComments;
    private EditText addComment;
    private Button send;
    private TextView btnClose;


    public ViewPostDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ViewPostDialogFragment newInstance(Post post) {
        ViewPostDialogFragment frag = new ViewPostDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("post", post);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_post, container);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Post post = getArguments().getParcelable("post");
        ivProfilePic = view.findViewById(R.id.ivProfilePicture);
        tvName = view.findViewById(R.id.tvName);
        tvDescription = view.findViewById(R.id.tvDescription);
        rvImages = view.findViewById(R.id.rvPictureContainer);
        btnClose = view.findViewById(R.id.btnClose);
        rvComments = view.findViewById(R.id.rvComments);
        addComment = view.findViewById(R.id.addComment);
        send = view.findViewById(R.id.btnSend);

        List<Comment> previousComments = post.getComments();
        CommentsAdapter adapter = new CommentsAdapter(getContext(), previousComments);

        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));

        tvName.setText(post.getUser().getUsername());
        ParseFile file = post.getUser().getParseFile("profilePic");
        if(file!= null){
            Glide.with(getContext()).load(file.getUrl()).transform(new CircleCrop()).into(ivProfilePic);
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

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredComment = addComment.getText().toString();
                SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String format = s.format(new Date());

                Comment comment = new Comment(ParseUser.getCurrentUser().getObjectId(), enteredComment, format);
                post.addComment(comment);
                previousComments.add(comment);
                adapter.notifyDataSetChanged();
                addComment.setText("");
            }
        });

        tvDescription.setText(post.getDescription());
        PostImagesAdapter adapterImages = new PostImagesAdapter(getContext(), post, ParseUser.getCurrentUser().getUsername());
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvImages.setLayoutManager(HorizontalLayout);
        rvImages.setAdapter(adapterImages);
        adapterImages.notifyDataSetChanged();
    }
}