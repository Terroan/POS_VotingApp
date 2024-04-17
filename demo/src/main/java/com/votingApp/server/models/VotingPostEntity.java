package com.votingApp.server.models;

import java.util.HashMap;

public class VotingPostEntity {

    private String voter;
    private HashMap<String, Integer> votes; //-> format: "1:2" , "question:vote"

    public VotingPostEntity() {

    }

    public VotingPostEntity(String voter, HashMap<String, Integer> votes) {
        this.voter = voter;
        this.votes = votes;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public HashMap<String, Integer> getVotes() {
        return votes;
    }

    public VotingPostEntity setVotes(HashMap<String, Integer> votes) {
        this.votes = votes;
        return this;
    }

    @Override
    public String toString() {
        return "VotingPostEntity{" +
                "voter=" + voter +
                ", votes=" + votes +
                '}';
    }
}
