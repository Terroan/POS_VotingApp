package com.votingApp.server.repositories;

import com.votingApp.server.models.VoterEntity;

public interface IUserRepository {

    boolean addUser(VoterEntity user);

    boolean checkUser(VoterEntity user);
}

