package com.example.docchi.model;

import android.util.Log;

import com.example.docchi.model.Image;
import com.example.docchi.model.Images;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;


@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";


    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ArrayList<Image> getImages(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Images");
        Images imagesCol = (Images) get(KEY_IMAGES);
        Images im = null;
        try {
            im = (Images) query.get(imagesCol.getObjectId());
            return im.getImages();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateImages(ArrayList<Image> imageList){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Images");
        Images images = (Images) get(KEY_IMAGES);
        query.whereEqualTo("objectId", images.getObjectId());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                    Images images = (Images) object;
                    images.setImages(imageList);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.e(getClass().getSimpleName(), "User update error: " + e);
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                }

            }
        });
        put(KEY_IMAGES, images);
    }

    public void setImages(ArrayList<Image> imageList){
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

    public int previousVote(String username){
        ArrayList<Image> images = getImages();
        for(int i=0; i<images.size(); i++){
            if(images.get(i).getWhoVoted().contains(username)){
                return i;
            }
        }
        return -1;
    }


}
