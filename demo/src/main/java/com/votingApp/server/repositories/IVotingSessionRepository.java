package com.votingApp.server.repositories;

import com.votingApp.server.models.VotingSessionEntity;

import java.util.List;

public interface IVotingSessionRepository {
    VotingSessionEntity create(VotingSessionEntity votingSessionEntity);

    VotingSessionEntity read(String id);

    List<VotingSessionEntity> readAll();

    VotingSessionEntity update(VotingSessionEntity votingSessionEntity);

    void delete(String id);
}
