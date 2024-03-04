package com.votingApp.server.models;

import org.bson.types.ObjectId;

public class VoterEntity {
    private ObjectId id;
    private String name;

    public VoterEntity() {

    }

    public VoterEntity(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public VoterEntity setId(ObjectId id) {
        this.id = id;
        return this;
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
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
