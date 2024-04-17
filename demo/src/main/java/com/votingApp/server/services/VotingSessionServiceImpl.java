package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.dtos.VotingSessionWithPasswordDTO;
import com.votingApp.server.models.VotingSessionEntity;
import com.votingApp.server.repositories.IUserRepository;
import com.votingApp.server.repositories.IVotingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingSessionServiceImpl implements IVotingSessionService {

    private final IVotingSessionRepository votingSessionRepository;
    private final IUserRepository votingSessionPasswordRepository;

    public VotingSessionServiceImpl(IVotingSessionRepository votingSessionRepository, IUserRepository votingSessionPasswordRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.votingSessionPasswordRepository = votingSessionPasswordRepository;
    }

    public boolean checkPassword(String id, String password) {
        return votingSessionPasswordRepository.checkPassword(id, password);
    }

    @Override
    public VotingSessionDTO create(VotingSessionWithPasswordDTO votingSessionWithPasswordDTO) {
        VotingSessionDTO tmp = new VotingSessionDTO(votingSessionRepository.create(votingSessionWithPasswordDTO.toVotingSessionEntity()));
        votingSessionPasswordRepository.addEntry(tmp.sessionID(), votingSessionWithPasswordDTO.password());
        return tmp;
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
    public VotingSessionDTO update(String sessionID, VotingSessionWithPasswordDTO votingSessionWithPasswordDTO) {
        VotingSessionEntity vse = votingSessionRepository.update(sessionID, votingSessionWithPasswordDTO.toVotingSessionEntity(), false);
        votingSessionPasswordRepository.updateSessionID(sessionID, vse.getSessionID());
        return new VotingSessionDTO(vse);
    }

    @Override
    public VotingPostDTO postResults(String sessionID, VotingPostDTO votingPostDTO) {
        votingSessionRepository.postResults(sessionID, votingPostDTO);
        return votingPostDTO;
    }
}
