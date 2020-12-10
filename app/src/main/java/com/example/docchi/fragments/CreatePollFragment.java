package com.example.docchi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.docchi.AboutActivity;
import com.example.docchi.CloseFragmentDialog;
import com.example.docchi.HelpActivity;
import com.example.docchi.LoginActivity;
import com.example.docchi.MainActivity;
import com.example.docchi.R;
import com.example.docchi.SettingActivity;
import com.example.docchi.model.Poll;
import com.example.docchi.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class CreatePollFragment extends Fragment {

    public static final String TAG = "CreatePollFragment";
    EditText etQuestion;
    List<EditText>  options;
    TextView btnPost;
    ImageView ivProfilePic;
    TextView tvUsername;
    TextView tvName;
    LinearLayout linearLayout;
    FloatingActionButton btnNewOption;
    ImageView ivCloseFragment;

    public CreatePollFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.About:
                Intent intent1 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent1);
                return true;
            case R.id.Help:
                Intent intent2 = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent2);
                return true;
            case R.id.Settings:
                Intent intent3 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent3);
                return true;
            case R.id.Logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        ((MainActivity) getActivity()).finish();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_poll, container, false);
        ActionBar actionBar = ((MainActivity) getContext()).getSupportActionBar();
        actionBar.setTitle("New Post");
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfilePic = view.findViewById(R.id.profilePic);
        tvUsername = view.findViewById(R.id.username);
        tvName = view.findViewById(R.id.name);
        etQuestion = view.findViewById(R.id.compose);
        options = new ArrayList<>();
        EditText edOption1 = view.findViewById(R.id.Option1);
        options.add(edOption1);
        linearLayout = view.findViewById(R.id.linerLayout);
        btnPost = view.findViewById(R.id.createPost);
        btnNewOption = view.findViewById(R.id.floatingActionButton);
        ivCloseFragment = view.findViewById(R.id.ivCloseFragment);

        ivCloseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        tvUsername.setText(ParseUser.getCurrentUser().getUsername());
        String fullname = ParseUser.getCurrentUser().getString("firstname") +
                " " + ParseUser.getCurrentUser().getString("lastname");
        tvName.setText(fullname);
        ParseFile file = ParseUser.getCurrentUser().getParseFile("profilePic");
        if(file!= null){
            Glide.with(getContext()).load(file.getUrl()).transform(new CircleCrop()).into(ivProfilePic);
        } else {
            Glide.with(getContext()).load(R.drawable.default_pic).transform(new CircleCrop()).into(ivProfilePic);
        }

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });

        btnNewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etd = new EditText(getContext());
                int next = options.size()+1;
                etd.setHint("Option " + next + "...");
                linearLayout.addView(etd);
                options.add(etd);
            }
        });
    }

    private void openDialog() {
        CloseFragmentDialog closeFragmentDialog = new CloseFragmentDialog();
        closeFragmentDialog.show(getFragmentManager(), "dialog");
    }

    private void post(){
        Post post = new Post();
        post.setDescription(etQuestion.getText().toString());
        post.setUser(ParseUser.getCurrentUser());
        List<Poll> polls = new ArrayList<>();
        for(int i=0; i<options.size(); i++){
            Poll poll = new Poll(options.get(i).getText().toString());
            polls.add(poll);
        }
        post.setPoll(polls);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    //if upload fails
                    Toast.makeText(getContext(), "Error while uploading post. Try again later.",Toast.LENGTH_SHORT).show();
                }
                ((MainActivity) getActivity()).setHome();
            }
        });
    }
}