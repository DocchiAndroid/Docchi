package com.example.docchi.fragments;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.docchi.MainActivity;
import com.example.docchi.Post;
import com.example.docchi.R;
import com.example.docchi.adapters.ViewPagerAdapter;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class CreatePollFragment extends Fragment {

    public static final String TAG = "CreatePollFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public final static int PICK_PHOTO_CODE = 328;
    private ImageButton btnCaptureImage;
    private ImageButton btnSelectImage;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private EditText etDescription;
    private TextView btnPost;
    private ArrayList<File> photoFiles;
    public String photoFileName = "docchi_img";
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogBuilder;


    public CreatePollFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_poll, container, false);
    }


    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCaptureImage = view.findViewById(R.id.btnCaptureImageCreatePoll);
        btnSelectImage = view.findViewById(R.id.btnSelectImageCreatePoll);
        viewPager = view.findViewById(R.id.viewPagerCreatePoll);
        etDescription = view.findViewById(R.id.etDescriptionCreatePoll);
        btnPost = view.findViewById(R.id.btnPostCreatePoll);
        photoFiles = new ArrayList<>();


        viewPagerAdapter = new ViewPagerAdapter((MainActivity) getContext(), photoFiles);
        viewPager.setAdapter(viewPagerAdapter);

        //resize certain UI components
        fixUI();

        //capture image listener
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        //file selector
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickPhoto(view);
            }
        });

        //post
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFiles.size() < 2) {  //|| ivPostImage.getDrawable() == null
                    Toast.makeText(getContext(), "At least two images are required to create a poll.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, photoFiles);
            }
        });
    }

    private void savePost(String description, ParseUser currentUser, ArrayList<File> photoFiles) {
        showDialogProgressBar();
        Post post = new Post();
        post.setDescription(description);
        post.setUser(currentUser);
        post.setImages(photoFiles);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving.....", e);
                    Toast.makeText(getContext(), "Error while posting", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
                ((MainActivity) getActivity()).setHome();
            }
        });

    }

    //launch camera to take photo
    private void launchCamera() {

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access

        photoFiles.add(getPhotoFileUri());

        // wrap File object into a content provider
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.example.fileprovider", photoFiles.get(photoFiles.size() - 1));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(((MainActivity) getContext()).getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    //activity result handler for camera and file selector
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                //Bitmap takenImage = BitmapFactory.decodeFile(photoFiles.get(photoFiles.size()-1).getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                //ivPostImage.setImageBitmap(takenImage);

                //display the image on screen
                viewPagerAdapter.notifyDataSetChanged();

            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }

        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();

            // Load the image located at photoUri into selectedImage
            File selectedImage = loadFromUri(photoUri);
            photoFiles.add(selectedImage);

            //display the image on screen
            viewPagerAdapter.notifyDataSetChanged();

            //Bitmap takenImage = BitmapFactory.decodeFile(selectedImage.getAbsolutePath());
            //ivPostImage.setImageBitmap(takenImage);

        }
    }

    //create a file using unique file name
    public File getPhotoFileUri() {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.

        //get timestamp to append to file
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
        String timestamp = simpleDateFormat.format(new Date());

        String fileName = photoFileName + timestamp + ".jpg";
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    //return file from uri
    public File loadFromUri(Uri photoUri) {
        File image = getPhotoFileUri();
        InputStream inputStream = null;
        try {
            inputStream = ((MainActivity) getContext()).getContentResolver().openInputStream(photoUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    //resize buttons and layout
    private void fixUI() {
        //get display width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        ActionBar actionBar = ((MainActivity) getContext()).getSupportActionBar();

        actionBar.setTitle("New Poll");

        //resize viewpager to hold images
        ViewGroup.LayoutParams viewPagerParams = viewPager.getLayoutParams();
        viewPagerParams.height = viewPagerParams.width;
        viewPager.setLayoutParams(viewPagerParams);

        //resize camera button to cover half of screen width
        ViewGroup.LayoutParams btnCaptureImageLayoutParams = btnCaptureImage.getLayoutParams();
        btnCaptureImageLayoutParams.width = width / 2;
        btnCaptureImage.setLayoutParams(btnCaptureImageLayoutParams);

        //resize file selector button to cover half of screen width
        ViewGroup.LayoutParams btnSelectImageLayoutParams = btnSelectImage.getLayoutParams();
        btnSelectImageLayoutParams.width = width / 2;
        btnSelectImage.setLayoutParams(btnSelectImageLayoutParams);
    }

    public void showDialogProgressBar() {

        dialogBuilder = new AlertDialog.Builder((MainActivity) getContext());
        dialogBuilder.setTitle("");
        final View v = getLayoutInflater().inflate(R.layout.progress_dialog, null);
        TextView t = v.findViewById(R.id.text_progress_bar);
        t.setText("Uploading.....");
        dialogBuilder.setView(v);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}