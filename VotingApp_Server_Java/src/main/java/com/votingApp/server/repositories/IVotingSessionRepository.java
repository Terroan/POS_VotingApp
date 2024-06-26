package com.votingApp.server.repositories;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingPostEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface IVotingSessionRepository {
    ObjectId create(VotingSessionEntity votingSessionEntity);

    String startSession(ObjectId id);

    VotingSessionEntity endSession(ObjectId id);

    VotingSessionEntity read(String sessionID);

    List<VotingSessionEntity> readAll();

    List<VotingSessionEntity> findAllById(List<ObjectId> ids);

    boolean update(Object id, VotingSessionEntity newVse);

    boolean delete(ObjectId id);

    Long deleteAll();

    VotingPostEntity postResults(String sessionID, VotingPostEntity votingPostEntity);
}
