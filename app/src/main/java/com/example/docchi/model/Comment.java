package com.example.docchi.model;

import com.parse.ParseUser;

public class Comment {
    private String description;
    private ParseUser parseUser;
    private String time;

    public Comment(String userId, String description, String time) {
        this.description = description;
        this.parseUser = Post.getUserById(userId);
        this.time = time;
    }

    public ParseUser getParseUser() {
        return parseUser;
    }

    public void setParseUser(ParseUser parseUser) {
        this.parseUser = parseUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
