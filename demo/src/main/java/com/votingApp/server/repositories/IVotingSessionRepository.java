package com.votingApp.server.repositories;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.models.VotingSessionEntity;

import java.util.List;

public interface IVotingSessionRepository {
    VotingSessionEntity create(VotingSessionEntity votingSessionEntity);

    VotingSessionEntity read(String sessionID);

    List<VotingSessionEntity> readAll();

    VotingSessionEntity update(String sessionID, VotingSessionEntity votingSessionEntity, boolean results);

    Long delete(String sessionID);

    Long deleteAll();

    VotingPostDTO postResults(String sessionID, VotingPostDTO votingPostDTO);
}
