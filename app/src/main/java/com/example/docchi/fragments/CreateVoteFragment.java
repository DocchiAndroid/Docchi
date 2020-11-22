package com.example.docchi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.docchi.R;

import java.io.File;


public class CreateVoteFragment extends Fragment {

  public static final String TAG = "CreateVoteFragment";
  public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
  private EditText etDescription;
  private Button btnCaptureImage;
  private ImageView ivPostImage;
  private Button btnSubmit;
  public Button btnLogOut;
  private File photoFile;
  public String photoFileName = "chewy.jpg";


  public CreateVoteFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_create_vote, container, false);
  }


  // This event is triggered soon after onCreateView().
  // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    etDescription = view.findViewById(R.id.etDescription);
    btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
    ivPostImage = view.findViewById(R.id.ivPostImage);
    btnSubmit = view.findViewById(R.id.btnSubmit);
    btnLogOut = view.findViewById(R.id.btnLogout);

  }

}