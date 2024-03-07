package com.votingApp.server.dtos;

import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingQuestionEntity;

public record VoterDTO(
        String name) {

    public VoterDTO(VoterEntity ve) {
        this(ve.getName());
    }

    public VoterEntity toVoterEntity() {
        return new VoterEntity(name);
    }
}
