package com.votingApp.server.dtos;

import com.votingApp.server.models.VotingPostEntity;
import com.votingApp.server.models.VotingQuestionEntity;

import java.util.HashMap;

public record VotingPostDTO(
        HashMap<Integer, Integer> votes) {

    public VotingPostDTO(VotingPostEntity vpe) {
        this(vpe.getVotes());
    }

    public VotingPostEntity toVotingPostEntity() {
        return new VotingPostEntity(votes);
    }
}
