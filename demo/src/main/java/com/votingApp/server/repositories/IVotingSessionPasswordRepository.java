package com.votingApp.server.repositories;

public interface IVotingSessionPasswordRepository {

     void addEntry(String sessionID, String password);
    boolean checkPassword(String sessionID, String password);
}

