package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.dtos.VotingSessionWithPasswordDTO;
import com.votingApp.server.models.VotingSessionEntity;

import java.util.List;

// COMPLET CHANGE
public interface IVotingSessionService {

    VotingSessionDTO create(VotingSessionWithPasswordDTO votingSessionWithPasswordDTO);

    VotingSessionDTO read(String sessionID);

    List<VotingSessionDTO> readAll();

    VotingSessionDTO update(String sessionID, VotingSessionWithPasswordDTO votingSessionWithPasswordDTO);

    Long delete(String sessionID);

    Long deleteAll();

    VotingPostDTO postResults(String sessionID, VotingPostDTO votingPostDTO);
}
