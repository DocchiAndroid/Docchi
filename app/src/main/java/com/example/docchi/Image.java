package com.example.docchi;

import com.parse.ParseFile;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Image {
    private ParseFile imageUrl;
    private ArrayList<String> who_voted;

    public Image(ParseFile imageUrl, JSONArray who_voted) {
        this.imageUrl = imageUrl;
        this.who_voted = new ArrayList<>();
        if (who_voted != null) {
            for (int i=0;i<who_voted.length();i++){
                try {
                    this.who_voted.add(who_voted.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Image(ParseFile imageUrl){
        this.imageUrl = imageUrl;
        this.who_voted = new ArrayList<>();
    }

    public ParseFile getImageUrl() {
        return imageUrl;
    }

    public ArrayList<String> getWhoVoted() {
        return who_voted;
    }

    public boolean changeVote(String newVote) {
        if(!who_voted.contains(newVote)){
            who_voted.add(newVote);
            return true;
        }
        who_voted.remove(newVote);
        return false;
    }

    public int getCount(){
        return who_voted.size();
    }
}
