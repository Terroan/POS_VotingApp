package com.votingApp.server.models;

import org.bson.types.ObjectId;

public class VoterEntity {
    private String name;

    public VoterEntity() {

    }

    public VoterEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public VoterEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "name='" + name + '\'' +
                '}';
    }
}
