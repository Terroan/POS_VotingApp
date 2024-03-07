package com.votingApp.server.models;

import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.List;

public class VotingPostEntity {
    private HashMap<Integer, Integer> votes; //-> format: "1:2" , "question:vote"

    public VotingPostEntity() {

    }

    public VotingPostEntity(HashMap<Integer, Integer> votes) {
        this.votes = votes;
    }

    public HashMap<Integer, Integer> getVotes() {
        return votes;
    }

    public VotingPostEntity setVotes(HashMap<Integer, Integer> votes) {
        this.votes = votes;
        return this;
    }

    @Override
    public String toString() {
        return "VotingPost{" +
                "votes=" + votes +
                '}';
    }
}
