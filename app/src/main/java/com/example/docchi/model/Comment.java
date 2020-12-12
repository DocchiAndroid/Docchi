package com.example.docchi.model;

import com.parse.ParseUser;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Comment {
    private String description;
    private ParseUser parseUser;
    private String time;

    public Comment(String userId, String description, String time) {
        this.description = description;
        this.parseUser = Post.getUserById(userId);
        this.time = time;
    }

    public Comment(String userId, String description) {
        this.description = description;
        this.parseUser = Post.getUserById(userId);
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.time = s.format(new Date());
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

    public String getTimeDifference(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(time);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            Calendar calendar = Calendar.getInstance();
            TimeZone fromTimeZone = calendar.getTimeZone();
            TimeZone toTimeZone = TimeZone.getTimeZone("MST");

            calendar.setTimeZone(fromTimeZone);
            calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
            if (fromTimeZone.inDaylightTime(calendar.getTime())) {
                calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
            }

            calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
            if (toTimeZone.inDaylightTime(calendar.getTime())) {
                calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
            }

            Timestamp curr_timestamp = new Timestamp(calendar.getTime().getTime());

            // get time difference in seconds
            long milliseconds = curr_timestamp.getTime() - timestamp.getTime();
            int seconds = (int) milliseconds / 1000;

            // calculate hours minutes and seconds
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int days = hours / 24;

            if(days != 0){
                if(days == 1)
                    return "1 day ago";
                if(days > 7)
                    return getTime().substring(0, 10);
                return days + " days ago";
            } else if(hours != 0){
                if(hours == 1)
                    return "1 hour ago";
                return hours + " hours ago";
            }

            if(minutes == 1)
                return "1 min ago";
            return minutes + " mins ago";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Just now";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
