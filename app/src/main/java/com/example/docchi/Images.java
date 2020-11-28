package com.example.docchi;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;

@ParseClassName("Images")
public class Images extends ParseObject {

    public static final String KEY_NUMBER_OF_IMAGES = "nImages";
    public static final String KEY_IMAGE0 = "image0";
    public static final String KEY_IMAGE1 = "image1";
    public static final String KEY_IMAGE2 = "image2";
    public static final String KEY_IMAGE3 = "image3";
    public static final String KEY_IMAGE4 = "image4";

    public static final String KEY_IMAGE0_WHO_VOTED = "image0_who_voted";
    public static final String KEY_IMAGE1_WHO_VOTED = "image1_who_voted";
    public static final String KEY_IMAGE2_WHO_VOTED = "image2_who_voted";
    public static final String KEY_IMAGE3_WHO_VOTED = "image3_who_voted";
    public static final String KEY_IMAGE4_WHO_VOTED = "image4_who_voted";

    public void setImages(ArrayList<Image> imageInfo) {
        int nImages = imageInfo.size();
        put(KEY_NUMBER_OF_IMAGES, nImages);
        if (nImages >=1) {
            put(KEY_IMAGE0, imageInfo.get(0).getImageUrl());
            put(KEY_IMAGE0_WHO_VOTED, imageInfo.get(0).getWhoVoted());
        }
        if (nImages >= 2) {
            put(KEY_IMAGE1, imageInfo.get(1).getImageUrl());
            put(KEY_IMAGE1_WHO_VOTED, imageInfo.get(1).getWhoVoted());
        }
        if (nImages >= 3) {
            put(KEY_IMAGE2, imageInfo.get(2).getImageUrl());
            put(KEY_IMAGE2_WHO_VOTED, imageInfo.get(2).getWhoVoted());
        }
        if (nImages >= 4) {
            put(KEY_IMAGE3, imageInfo.get(3).getImageUrl());
            put(KEY_IMAGE3_WHO_VOTED, imageInfo.get(3).getWhoVoted());
        }
        if (nImages == 5) {
            put(KEY_IMAGE4, imageInfo.get(4).getImageUrl());
            put(KEY_IMAGE4_WHO_VOTED, imageInfo.get(4).getWhoVoted());
        };
    }

    public ArrayList<Image> getImages() {
        int nImages = getInt(KEY_NUMBER_OF_IMAGES);
        ArrayList<Image> imageInfo = new ArrayList<>();
        if (nImages >=1) imageInfo.add(new Image(getParseFile(KEY_IMAGE0), getJSONArray(KEY_IMAGE0_WHO_VOTED)));
        if (nImages >= 2) imageInfo.add(new Image(getParseFile(KEY_IMAGE1), getJSONArray(KEY_IMAGE1_WHO_VOTED)));
        if (nImages >= 3) imageInfo.add(new Image(getParseFile(KEY_IMAGE2), getJSONArray(KEY_IMAGE2_WHO_VOTED)));
        if (nImages >= 4) imageInfo.add(new Image(getParseFile(KEY_IMAGE3), getJSONArray(KEY_IMAGE3_WHO_VOTED)));
        if (nImages == 5) imageInfo.add(new Image(getParseFile(KEY_IMAGE4), getJSONArray(KEY_IMAGE4_WHO_VOTED)));
        return imageInfo;
    }
}