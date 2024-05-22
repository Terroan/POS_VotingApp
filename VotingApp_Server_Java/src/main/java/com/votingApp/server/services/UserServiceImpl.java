package com.votingApp.server.services;

import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.repositories.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(VoterEntity user) {
        return userRepository.addUser(user);
    }

    public VoterEntity checkUser(VoterEntity user) {
        return userRepository.checkUser(user);
    }
}