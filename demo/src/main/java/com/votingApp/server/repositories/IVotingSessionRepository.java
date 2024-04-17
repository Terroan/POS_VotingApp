package com.votingApp.server.repositories;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface IVotingSessionRepository {
    VotingSessionEntity create(VotingSessionEntity votingSessionEntity);

    boolean startSession(ObjectId id);

    boolean endSession(ObjectId id);

    VotingSessionEntity read(String sessionID);

    List<VotingSessionEntity> readAll();

    List<VotingSessionEntity> findAllById(List<ObjectId> ids);

    boolean update(Object id, VotingSessionEntity newVse);

    boolean delete(ObjectId id);

    Long deleteAll();

    boolean postResults(String sessionID, VotingPostDTO votingPostDTO);
}
