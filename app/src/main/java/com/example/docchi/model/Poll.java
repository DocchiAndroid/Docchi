package com.example.docchi.model;

import java.util.ArrayList;
import java.util.List;

public class Poll {
    private String itemDescription;
    private List<String> who_voted;

    public Poll(String itemDescription){
        this.itemDescription = itemDescription;
        this.who_voted = new ArrayList<>();
    }

    public Poll(String itemDescription, List<String> votes){
        this.itemDescription = itemDescription;
        this.who_voted = votes;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public List<String> getWhoVoted(){return who_voted;}

    public int getVotes() {
        return who_voted.size();
    }

    public boolean changeVote(String newVote) {
        if(!who_voted.contains(newVote)){
            who_voted.add(newVote);
            return true;
        }
        who_voted.remove(newVote);
        return false;
    }

    public boolean isVoted(String username){
        if(who_voted.contains(username))
            return true;
        return false;
    }
}
