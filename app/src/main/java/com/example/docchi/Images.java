package com.example.docchi;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

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

    public static final String KEY_IMAGE0_VOTE = "image0_vote";
    public static final String KEY_IMAGE1_VOTE = "image1_vote";
    public static final String KEY_IMAGE2_VOTE = "image2_vote";
    public static final String KEY_IMAGE3_VOTE = "image3_vote";
    public static final String KEY_IMAGE4_VOTE = "image4_vote";

    public void setImages(ArrayList<Image> imageInfo) {
        int nImages = imageInfo.size();
        put(KEY_NUMBER_OF_IMAGES, nImages);
        if (nImages >=1) {
            put(KEY_IMAGE0, imageInfo.get(0).getImageUrl());
            put(KEY_IMAGE0_VOTE, imageInfo.get(0).getCount());
        }
        if (nImages >= 2) {
            put(KEY_IMAGE1, imageInfo.get(1).getImageUrl());
            put(KEY_IMAGE1_VOTE, imageInfo.get(1).getCount());
        }
        if (nImages >= 3) {
            put(KEY_IMAGE2, imageInfo.get(2).getImageUrl());
            put(KEY_IMAGE2_VOTE, imageInfo.get(2).getCount());
        }
        if (nImages >= 4) {
            put(KEY_IMAGE3, imageInfo.get(3).getImageUrl());
            put(KEY_IMAGE3_VOTE, imageInfo.get(3).getCount());
        }
        if (nImages == 5) {
            put(KEY_IMAGE4, imageInfo.get(4).getImageUrl());
            put(KEY_IMAGE4_VOTE, imageInfo.get(4).getCount());
        };
    }

    public ArrayList<Image> getImages() {
        int nImages = getInt(KEY_NUMBER_OF_IMAGES);
        ArrayList<Image> imageInfo = new ArrayList<>();
        if (nImages >=1) imageInfo.add(new Image(getParseFile(KEY_IMAGE0), getInt(KEY_IMAGE0_VOTE)));
        if (nImages >= 2) imageInfo.add(new Image(getParseFile(KEY_IMAGE1), getInt(KEY_IMAGE1_VOTE)));
        if (nImages >= 3) imageInfo.add(new Image(getParseFile(KEY_IMAGE2), getInt(KEY_IMAGE2_VOTE)));
        if (nImages >= 4) imageInfo.add(new Image(getParseFile(KEY_IMAGE3), getInt(KEY_IMAGE3_VOTE)));
        if (nImages == 5) imageInfo.add(new Image(getParseFile(KEY_IMAGE4), getInt(KEY_IMAGE4_VOTE)));
        return imageInfo;
    }
}