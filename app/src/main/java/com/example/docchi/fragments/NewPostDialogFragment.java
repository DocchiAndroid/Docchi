package com.example.docchi.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.docchi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NewPostDialogFragment extends DialogFragment {

    private Button withImages;
    private Button withoutImages;

    public NewPostDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static NewPostDialogFragment newInstance(String title) {
        NewPostDialogFragment frag = new NewPostDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_post_diaglog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        withImages = view.findViewById(R.id.poll);
        withoutImages = view.findViewById(R.id.pollWithoutPicture);
        withImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = unwrap(view.getContext());
                Fragment myFragment = new CreatePostFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, myFragment).addToBackStack(null).commit();
                dismiss();
            }
        });

        withoutImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = unwrap(view.getContext());
                Fragment myFragment = new CreatePollFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, myFragment).addToBackStack(null).commit();
                dismiss();
            }
        });
    }

    private static AppCompatActivity unwrap(Context context) {
        while (!(context instanceof AppCompatActivity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (AppCompatActivity) context;
    }
}