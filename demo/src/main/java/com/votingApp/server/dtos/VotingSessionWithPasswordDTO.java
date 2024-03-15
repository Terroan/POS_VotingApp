package com.votingApp.server.dtos;

import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public record VotingSessionWithPasswordDTO(
        String id,
        VoterDTO creator,
        List<VotingQuestionDTO> questions,
        String password) {

    public VotingSessionEntity toVotingSessionEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new VotingSessionEntity(_id,
                creator.toVoterEntity(),
                questions.stream().map(VotingQuestionDTO::toVotingQuestionEntity).toList());
    }
}
