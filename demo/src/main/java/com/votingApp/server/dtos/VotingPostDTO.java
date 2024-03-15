package com.votingApp.server.dtos;

import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingPostEntity;
import com.votingApp.server.models.VotingQuestionEntity;
import org.bson.types.ObjectId;

import java.util.HashMap;

public record VotingPostDTO(

        VoterEntity voter,
        HashMap<String, Integer> votes) {

    public VotingPostDTO(VotingPostEntity vpe) {
        this(vpe.getVoter(), vpe.getVotes());
    }

    public VotingPostEntity toVotingPostEntity() {
        return new VotingPostEntity(voter, votes);
    }
}
