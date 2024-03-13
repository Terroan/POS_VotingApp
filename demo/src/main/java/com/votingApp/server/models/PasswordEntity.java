package com.votingApp.server.models;

public class PasswordEntity {
    private String sessionID;
    private String password;

    public PasswordEntity(String sessionID, String password) {
        this.sessionID = sessionID;
        this.password = password;
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
}
