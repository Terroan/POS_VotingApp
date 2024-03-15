package com.votingApp.server.models;

import org.bson.types.ObjectId;

public class PasswordEntity {

    private ObjectId id;
    private String sessionID;
    private String password;

    public PasswordEntity() {

    }

    public PasswordEntity(ObjectId id, String sessionID, String password) {
        this.id = id;
        this.sessionID = sessionID;
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordEntity{" +
                "id=" + id +
                ", sessionID='" + sessionID + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
