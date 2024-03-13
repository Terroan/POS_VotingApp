package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.dtos.VotingSessionWithPasswordDTO;
import com.votingApp.server.models.VotingSessionEntity;

import java.util.List;

public interface IVotingSessionService {

    boolean checkPassword(String id, String password);
    VotingSessionDTO create(VotingSessionWithPasswordDTO votingSessionWithPasswordDTO);

    VotingSessionDTO read(String id);

    List<VotingSessionDTO> readAll();

    VotingSessionDTO update(VotingSessionDTO votingSessionDTO);

    Long delete(String id);

    Long deleteAll();

    VotingPostDTO postResults(String id, VotingPostDTO votingPostDTO);
}
