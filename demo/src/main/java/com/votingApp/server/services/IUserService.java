package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.dtos.VotingSessionWithPasswordDTO;
import com.votingApp.server.models.VoterEntity;

import java.util.List;

public interface IUserService {

    boolean addUser(VoterEntity user);
    boolean checkUser(VoterEntity user);
}
