package com.votingApp.server.services;

import com.votingApp.server.dtos.VoterIngressDTO;
import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionExgressDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingSessionEntity;
import com.votingApp.server.repositories.IUserRepository;
import com.votingApp.server.repositories.IVotingSessionRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingSessionServiceImpl implements IVotingSessionService {

    private final IVotingSessionRepository votingSessionRepository;
    private final IUserRepository userRepository;

    public VotingSessionServiceImpl(IVotingSessionRepository votingSessionRepository, IUserRepository userRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public VotingSessionIngressDTO create(VotingSessionEntity votingSessionEntity, VoterEntity voterEntity) {
        VoterEntity ve = userRepository.checkUser(voterEntity);
        if(ve == null)
            return null;

        ve.getSessions().add(votingSessionRepository.create(votingSessionEntity).getId()); // add id to user's list
        return new VotingSessionIngressDTO(votingSessionEntity);
    }

    @Override
    public VotingSessionExgressDTO read(String id) { //session code
        VotingSessionEntity vse = votingSessionRepository.read(id);
        VoterEntity ve = userRepository.findBySessionId(vse.getId());
        if(vse != null)
            return new VotingSessionExgressDTO(vse, ve);
        return null;
    }

    @Override
    public List<VotingSessionIngressDTO> readAll() { return votingSessionRepository.readAll().stream().map(VotingSessionIngressDTO::new).toList(); }

    @Override
    public boolean delete(ObjectId id, VoterEntity creator) {
        VoterEntity ve = userRepository.checkUser(creator);
        if(ve != null && ve.getSessions().contains(id))
            return votingSessionRepository.delete(id);
        return false;
    }

    @Override
    public Long deleteAll() { return votingSessionRepository.deleteAll(); }

    @Override
    public boolean update(ObjectId id, VotingSessionEntity votingSessionEntity, VoterEntity creator) {
        VoterEntity ve = userRepository.checkUser(creator);
        if(ve != null && ve.getSessions().contains(id))
            return votingSessionRepository.update(id, votingSessionEntity);
        return false;
    }

    @Override
    public boolean postResults(String sessionID, VotingPostDTO votingPostDTO) {
        return votingSessionRepository.postResults(sessionID, votingPostDTO)
    }
}
