package com.example.docchi;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;


@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";


    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ArrayList<ParseFile> getImages(){
        Images images = (Images) get(KEY_IMAGES);
        return images.getImages();
    }

    public void setImages(ArrayList<File> imageList){
        Images images= new Images();
        images.setImages(imageList);
        images.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("setImages", "Error while saving. images....", e);
                }
            }
        });
        put(KEY_IMAGES, images);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }


}
