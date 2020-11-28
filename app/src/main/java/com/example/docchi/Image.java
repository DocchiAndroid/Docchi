package com.example.docchi;

import com.parse.ParseFile;

public class Image {
    private ParseFile imageUrl;
    private int count;

    public Image(ParseFile imageUrl, int count){
        this.imageUrl = imageUrl;
        this.count = count;
    }

    public Image(ParseFile imageUrl){
        this.imageUrl = imageUrl;
        this.count = 0;
    }

    public ParseFile getImageUrl() {
        return imageUrl;
    }

    public int getCount() {
        return count;
    }
}
