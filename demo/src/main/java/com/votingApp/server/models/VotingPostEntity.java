package com.votingApp.server.models;

import org.bson.types.ObjectId;

import java.util.List;

public class VotingPostEntity {
    private ObjectId id;
    private List<String> votes; //-> format: "1:2" , "question:vote"

    public VotingPostEntity() {

    }

    public VotingPostEntity(ObjectId id, List<String> votes) {
        this.id = id;
        this.votes = votes;
    }

    public ObjectId getId() {
        return id;
    }

    public VotingPostEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public List<String> getVotes() {
        return votes;
    }

    public VotingPostEntity setVotes(List<String> votes) {
        this.votes = votes;
        return this;
    }

    @Override
    public String toString() {
        return "VotingPost{" +
                "id=" + id +
                ", votes=" + votes +
                '}';
    }
}
