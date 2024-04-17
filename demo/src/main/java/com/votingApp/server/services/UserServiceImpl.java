package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.dtos.VotingSessionWithPasswordDTO;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingSessionEntity;
import com.votingApp.server.repositories.IUserRepository;
import com.votingApp.server.repositories.IVotingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(VoterEntity user) {
        return userRepository.addUser(user);
    }

    public boolean checkUser(VoterEntity user) {
        return userRepository.checkUser(user);
    }
}