package com.votingApp.server.services;

import com.votingApp.server.dtos.*;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingPostEntity;
import com.votingApp.server.models.VotingSessionEntity;
import com.votingApp.server.repositories.IUserRepository;
import com.votingApp.server.repositories.IVotingSessionRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ObjectId create(VotingSessionIngressDTO votingSessionIngressDTO, VoterIngressDTO voterIngressDTO) {
        VoterEntity ve = userRepository.checkUser(voterIngressDTO.toVoterEntity());
        if(ve == null)
            return null;

        if(ve.getSessions() == null)
            ve.setSessions(new ArrayList<>());
        ve.getSessions().add(votingSessionRepository.create(votingSessionIngressDTO.toVotingSessionEntity())); // add id to user's list
        userRepository.update(ve);
        return ve.getSessions().get(ve.getSessions().size()-1);
    }

    @Override
    public String startSession(ObjectId id, VoterIngressDTO voterIngressDTO) {
        VoterEntity ve = userRepository.checkUser(voterIngressDTO.toVoterEntity());
        System.out.println(ve);
        if(ve == null)
            return null;

        if(ve.getSessions() == null)
            ve.setSessions(new ArrayList<>());

        for(ObjectId oid : ve.getSessions()) {
            System.out.println(oid.toString() +" -> " + id.toString());
            if(oid.toString().equals(id.toString()))
            {
                System.out.println("DRINN");
                return votingSessionRepository.startSession(oid);
            }
        }
        return null;
    }

    @Override
    public VotingSessionExgressDTO endSession(ObjectId id, VoterIngressDTO voterIngressDTO) {
        VoterEntity ve = userRepository.checkUser(voterIngressDTO.toVoterEntity());
        if(ve == null)
            return null;

        if(ve.getSessions() == null)
            ve.setSessions(new ArrayList<>());

        for(ObjectId oid : ve.getSessions()) {
            System.out.println(oid.toString() +" -> " + id.toString());
            if(oid.toString().equals(id.toString()))
            {
                return new VotingSessionExgressDTO(votingSessionRepository.endSession(oid));
            }
        }
        return null;
    }

    @Override
    public VotingSessionExgressDTO read(String id) { //session code
        VotingSessionEntity vse = votingSessionRepository.read(id);
        if(vse != null)
            return new VotingSessionExgressDTO(vse);
        return null;
    }

    @Override
    public List<VotingSessionIngressDTO> readAll() { return votingSessionRepository.readAll().stream().map(VotingSessionIngressDTO::new).toList(); }

    @Override
    public List<VotingSessionExgressDTO> readAllByUser(VoterIngressDTO voterIngressDTO) { //session code
        VoterEntity ve = userRepository.checkUser(voterIngressDTO.toVoterEntity());
        if(ve != null) {
            List<VotingSessionExgressDTO> list = new ArrayList<>();
            for(VotingSessionEntity vse : votingSessionRepository.findAllById(ve.getSessions())) {
                list.add(new VotingSessionExgressDTO(vse));
            }
            return list;
        }
        return null;
    }

    @Override
    public boolean delete(ObjectId id, VoterEntity creator) {
        VoterEntity ve = userRepository.checkUser(creator);
        if(ve != null && ve.getSessions().contains(id)) {
            ve.getSessions().remove(id);
            return votingSessionRepository.delete(id);
        }
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
    public VotingPostDTO postResults(String sessionID, VotingPostDTO votingPostDTO) {
        return new VotingPostDTO(votingSessionRepository.postResults(sessionID, votingPostDTO.toVotingPostEntity()));
    }
}