package com.votingApp.server.repositories;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.models.VotingSessionEntity;

import java.util.List;

public interface IVotingSessionRepository {
    VotingSessionEntity create(VotingSessionEntity votingSessionEntity);

    VotingSessionEntity read(String id);

    List<VotingSessionEntity> readAll();

    VotingSessionEntity update(VotingSessionEntity votingSessionEntity);

    Long delete(String id);

    Long deleteAll();

    VotingPostDTO postResults(String id, VotingPostDTO votingPostDTO);
}
