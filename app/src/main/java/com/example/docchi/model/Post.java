package com.example.docchi.model;

import android.util.JsonWriter;
import android.util.Log;
import android.widget.Toast;

import com.example.docchi.model.Image;
import com.example.docchi.model.Images;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_POLL = "poll";
    public static final String KEY_COMMENTS = "comments";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ArrayList<Image> getImages(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Images");
        Images imagesCol = (Images) get(KEY_IMAGES);
        Images im = null;
        try {
            if(imagesCol == null || imagesCol.getObjectId() == null)
                return null;
            im = (Images) query.get(imagesCol.getObjectId());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(im != null)
            return im.getImages();
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

    public int previousVoteImages(String username){
        ArrayList<Image> images = getImages();
        for(int i=0; i<images.size(); i++){
            if(images.get(i).getWhoVoted().contains(username)){
                return i;
            }
        }
        return -1;
    }

    public int previousVotePoll(String username){
        List<Poll> polls = getPolls();
        for(int i=0; i<polls.size(); i++){
            if(polls.get(i).getWhoVoted().contains(username)){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Poll> getPolls() {
        JSONArray jsonArray = getJSONArray(KEY_POLL);
        ArrayList<Poll> polls = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                String description = jsonArray.getJSONArray(i).getString(0);
                List<String> voted = new ArrayList<>();
                for(int j=1; j<jsonArray.getJSONArray(i).length(); j++){
                    voted.add(jsonArray.getString(j));
                }
                polls.add(new Poll(description, voted));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return polls;
    }

    public void setPoll(List<Poll> p) {
        List<List<String>> polls = new ArrayList<>();
        for (int i = 0; i < p.size(); i++) {
            List<String> poll = new ArrayList<>();
            poll.add(p.get(i).getItemDescription());
            poll.addAll(p.get(i).getWhoVoted());
            polls.add(poll);
        }
        put(KEY_POLL, polls);
        this.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
    }

    public List<Comment> getComments(){
        JSONArray jsonArray = getJSONArray(KEY_COMMENTS);
        List<Comment> comments = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                JSONArray element = jsonArray.getJSONArray(i);
                String user = element.getString(0);
                String content = element.getString(1);
                String time = element.getString(2);
                Comment comment = new Comment(user, content, time);
                comments.add(comment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return comments;
    }

    public void addComment(Comment comment) {
        List<Comment> comments = getComments();
        comments.add(comment);
        List<List<String>> storeComments = new ArrayList<>();
        for(Comment c : comments){
            List<String> curr = new ArrayList<>();
            curr.add(c.getParseUser().getObjectId());
            curr.add(c.getDescription());
            curr.add(c.getTime());
            storeComments.add(curr);
        }
        put(KEY_COMMENTS, storeComments);
        this.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
    }

    public static ParseUser getUserById(String id){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        try {
            ParseUser user = (ParseUser) query.get(id);
            return user;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotalImagesVotes(){
        List<Image> images = getImages();
        int total = 0;
        for(Image image: images){
            total += image.getCount();
        }
        return total;
    }

    public int getTotalPollVotes(){
        List<Poll> polls = getPolls();
        int total = 0;
        for(Poll poll: polls){
            total += poll.getVotes();
        }
        return total;
    }

    public int getTotalComments(){
        List<Comment> comments = getComments();
        return comments.size();
    }
}
