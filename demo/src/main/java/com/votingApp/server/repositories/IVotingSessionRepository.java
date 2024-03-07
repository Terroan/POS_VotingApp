package com.votingApp.server.repositories;

import com.votingApp.server.models.VotingSessionEntity;

public interface IVotingSessionRepository {
    VotingSessionEntity create(VotingSessionEntity votingSessionEntity);

    VotingSessionEntity read(String id);

    VotingSessionEntity update(VotingSessionEntity votingSessionEntity);

    void delete(String id);
}
