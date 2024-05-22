package com.votingApp.server.repositories;

import com.votingApp.server.models.VoterEntity;
import org.bson.types.ObjectId;

public interface IUserRepository {

    boolean addUser(VoterEntity user);

    VoterEntity checkUser(VoterEntity user);

    VoterEntity findBySessionId(ObjectId id);

    VoterEntity update(VoterEntity voterEntity);
}

