package com.votingApp.server.services;

import com.votingApp.server.models.VoterEntity;
import org.bson.types.ObjectId;

public interface IUserService {

    boolean addUser(VoterEntity user);
    VoterEntity checkUser(VoterEntity user);
}
