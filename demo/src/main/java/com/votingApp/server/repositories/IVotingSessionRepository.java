package com.votingApp.server.repositories;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface IVotingSessionRepository {
    VotingSessionEntity create(VotingSessionEntity votingSessionEntity);

    boolean startSession(ObjectId id, VoterEntity creator);

    boolean endSession(ObjectId id, VoterEntity creator);

    VotingSessionEntity read(String sessionID);

    List<VotingSessionEntity> readAll();

    List<VotingSessionEntity> findAllByUser(VoterEntity user);

    boolean update(Object id, VotingSessionEntity newVse, VoterEntity creator)

    boolean delete(ObjectId id, VoterEntity creator);

    Long deleteAll();

    boolean postResults(String sessionID, VotingPostDTO votingPostDTO)
}
