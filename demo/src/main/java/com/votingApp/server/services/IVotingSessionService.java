package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.models.VotingSessionEntity;

import java.util.List;

public interface IVotingSessionService {
    VotingSessionDTO create(VotingSessionDTO votingSessionDTO);

    VotingSessionDTO read(String id);

    List<VotingSessionDTO> readAll();

    VotingSessionDTO update(VotingSessionDTO votingSessionDTO);

    void delete(String id);
}
