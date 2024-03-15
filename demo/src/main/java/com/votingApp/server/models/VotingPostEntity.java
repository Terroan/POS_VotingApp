package com.votingApp.server.models;

import java.util.HashMap;

public class VotingPostEntity {

    private VoterEntity voter;
    private HashMap<String, Integer> votes; //-> format: "1:2" , "question:vote"

    public VotingPostEntity() {

    }

    public VotingPostEntity(VoterEntity voter, HashMap<String, Integer> votes) {
        this.voter = voter;
        this.votes = votes;
    }

    public VoterEntity getVoter() {
        return voter;
    }

    public void setVoter(VoterEntity voter) {
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
