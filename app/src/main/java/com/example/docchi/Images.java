package com.example.docchi;

import com.parse.ParseClassName;
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
    public static final String KEY_POST = "post";


    public void setImages(ArrayList<File> files) {
        int nImages = files.size();
        put(KEY_NUMBER_OF_IMAGES, nImages);
        if (nImages >=1) put(KEY_IMAGE0, new ParseFile(files.get(0)));
        if (nImages >= 2) put(KEY_IMAGE1, new ParseFile(files.get(1)));
        if (nImages >= 3) put(KEY_IMAGE2, new ParseFile(files.get(2)));
        if (nImages >= 4) put(KEY_IMAGE3, new ParseFile(files.get(3)));
        if (nImages == 5) put(KEY_IMAGE4, new ParseFile(files.get(4)));

    }
}