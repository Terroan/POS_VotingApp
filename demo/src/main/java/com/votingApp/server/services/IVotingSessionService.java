package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.models.VotingSessionEntity;

public interface IVotingSessionService {
    VotingSessionDTO create(VotingSessionDTO votingSessionDTO);

    VotingSessionDTO read(String id);

    VotingSessionDTO update(VotingSessionDTO votingSessionDTO);

    void delete(String id);
}
