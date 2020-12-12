package com.example.docchi.fragments;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.docchi.MainActivity;
import com.example.docchi.R;


public class NewPostDialogFragment extends DialogFragment {

    private Button withImages;
    private Button withoutImages;
    private ImageView btnClose;

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
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        btnClose = view.findViewById(R.id.dialog_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        withImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = new CreatePostFragment();
                ((MainActivity)getActivity()).getFragmentAdapter().replaceFragment(myFragment, 2);
                dismiss();
            }
        });

        withoutImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = new CreatePollFragment();
                ((MainActivity)getActivity()).getFragmentAdapter().replaceFragment(myFragment, 2);
                dismiss();
            }
        });
    }

}