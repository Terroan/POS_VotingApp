package com.votingApp.server.services;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionExgressDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface IVotingSessionService {

    VotingSessionIngressDTO create(VotingSessionEntity votingSessionEntity, VoterEntity voterEntity);

    VotingSessionExgressDTO read(String sessionID);

    List<VotingSessionIngressDTO> readAll();

    boolean update(ObjectId id, VotingSessionEntity votingSessionEntity, VoterEntity voterEntity);

    boolean delete(ObjectId id, VoterEntity voterEntity);

    Long deleteAll();

    boolean postResults(String sessionID, VotingPostDTO votingPostDTO);
}
