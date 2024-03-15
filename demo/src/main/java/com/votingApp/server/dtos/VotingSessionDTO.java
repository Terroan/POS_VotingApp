package com.votingApp.server.dtos;

import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingPostEntity;
import com.votingApp.server.models.VotingQuestionEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public record VotingSessionDTO(
        String id,
        String sessionID,
        VoterDTO creator,
        List<VotingQuestionDTO> questions,
        List<VotingPostDTO> results) {

    public VotingSessionDTO(VotingSessionEntity vse) {
        this(vse.getId() == null ? new ObjectId().toHexString() : vse.getId().toHexString(), vse.getSessionID(), new VoterDTO(vse.getCreator()),
                vse.getQuestions().stream().map(VotingQuestionDTO::new).toList(),
                (vse.getResults() == null ? new ArrayList<>() : vse.getResults().stream().map(VotingPostDTO::new).toList()));
    }
}
