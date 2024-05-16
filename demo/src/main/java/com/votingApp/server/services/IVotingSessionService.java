package com.votingApp.server.services;

import com.votingApp.server.dtos.*;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingPostEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface IVotingSessionService {

    ObjectId create(VotingSessionIngressDTO votingSessionIngressDTO, VoterIngressDTO voterIngressDTO);

    VotingSessionExgressDTO read(String sessionID);

    List<VotingSessionIngressDTO> readAll();

    List<VotingSessionExgressDTO> readAllByUser(VoterIngressDTO voterIngressDTO);

    String startSession(ObjectId id, VoterIngressDTO voterIngressDTO);
    VotingSessionExgressDTO endSession(ObjectId id, VoterIngressDTO voterIngressDTO);

    boolean update(ObjectId id, VotingSessionEntity votingSessionEntity, VoterEntity voterEntity);

    boolean delete(ObjectId id, VoterEntity voterEntity);

    Long deleteAll();

    VotingPostDTO postResults(String sessionID, VotingPostDTO votingPostDTO);
}
