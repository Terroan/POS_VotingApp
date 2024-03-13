package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.dtos.VotingSessionWithPasswordDTO;
import com.votingApp.server.models.PasswordEntity;
import com.votingApp.server.models.VotingSessionEntity;
import com.votingApp.server.repositories.IVotingSessionPasswordRepository;
import com.votingApp.server.repositories.IVotingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingSessionServiceImpl implements IVotingSessionService {

    private final IVotingSessionRepository votingSessionRepository;
    private final IVotingSessionPasswordRepository votingSessionPasswordRepository;

    public VotingSessionServiceImpl(IVotingSessionRepository votingSessionRepository, IVotingSessionPasswordRepository votingSessionPasswordRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.votingSessionPasswordRepository = votingSessionPasswordRepository;
    }

    public boolean checkPassword(String id, String password) {
        return votingSessionPasswordRepository.checkPassword(id, password);
    }

    @Override
    public VotingSessionDTO create(VotingSessionWithPasswordDTO votingSessionWithPasswordDTO) {
        votingSessionPasswordRepository.addEntry(votingSessionWithPasswordDTO.votingSessionDTO().sessionID(), votingSessionWithPasswordDTO.password());
        return new VotingSessionDTO(votingSessionRepository.create(votingSessionWithPasswordDTO.votingSessionDTO().toVotingSessionEntity()));
    }

    @Override
    public VotingSessionDTO read(String id) {
        VotingSessionEntity vse = votingSessionRepository.read(id);
        if(vse != null)
            return new VotingSessionDTO(vse);
        return null;
    }

    @Override
    public List<VotingSessionDTO> readAll() { return votingSessionRepository.readAll().stream().map(VotingSessionDTO::new).toList(); }

    @Override
    public Long delete(String id) {
        return votingSessionRepository.delete(id);
    }

    @Override
    public Long deleteAll() { return votingSessionRepository.deleteAll(); }

    @Override
    public VotingSessionDTO update(VotingSessionDTO votingSessionDTO) {
        return new VotingSessionDTO(votingSessionRepository.update(votingSessionDTO.toVotingSessionEntity()));
    }

    @Override
    public VotingPostDTO postResults(String id, VotingPostDTO votingPostDTO) {
        return votingSessionRepository.postResults(id, votingPostDTO);
    }
}
