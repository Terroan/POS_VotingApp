package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.models.VotingSessionEntity;
import com.votingApp.server.repositories.IVotingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingSessionServiceImpl implements IVotingSessionService {

    private final IVotingSessionRepository votingSessionRepository;

    public VotingSessionServiceImpl(IVotingSessionRepository votingSessionRepository) {this.votingSessionRepository = votingSessionRepository;}

    @Override
    public VotingSessionDTO create(VotingSessionDTO votingSessionDTO) { return new VotingSessionDTO(votingSessionRepository.create(votingSessionDTO.toVotingSessionEntity())); }

    @Override
    public VotingSessionDTO read(String id) {
        return new VotingSessionDTO(votingSessionRepository.read(id));
    }

    @Override
    public List<VotingSessionDTO> readAll() { return votingSessionRepository.readAll().stream().map(VotingSessionDTO::new).toList(); }

    @Override
    public void delete(String id) { votingSessionRepository.delete(id); }

    @Override
    public VotingSessionDTO update(VotingSessionDTO votingSessionDTO) {
        return new VotingSessionDTO(votingSessionRepository.update(votingSessionDTO.toVotingSessionEntity()));
    }
}
