package com.votingApp.server.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

public class VoterEntity {
    @Id
    private ObjectId id;
    private String name;
    private String password;
    @Field("sessions")
    private List<ObjectId> sessions;

    public VoterEntity() {

    }

    public VoterEntity(ObjectId id, String name, String password, List<ObjectId> sessions) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sessions = sessions;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<ObjectId> getSessions() {
        return sessions;
    }

    public void setSessions(List<ObjectId> sessions) {
        this.sessions = sessions;
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
                ", sessions=" + sessions +
                '}';
    }
}
