package com.votingApp.server.models;

import org.bson.types.ObjectId;

public class VoterEntity {
    private ObjectId id;
    private String name;
    private String password;

    public VoterEntity() {

    }

    public VoterEntity(ObjectId id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public VoterEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkUser(VoterEntity ve) {
        if(this.name.equals(ve.name) && this.password.equals(ve.password))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "VoterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
