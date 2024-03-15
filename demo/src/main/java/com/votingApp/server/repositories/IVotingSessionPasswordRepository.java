package com.votingApp.server.repositories;

import com.votingApp.server.models.PasswordEntity;

public interface IVotingSessionPasswordRepository {

     void addEntry(String sessionID, String password);
    boolean checkPassword(String sessionID, String password);
    void updateSessionID(String old, String neew);
}

