package com.votingApp.server.dtos;

import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingQuestionEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public record VotingSessionExgressDTO(
        String title,
        VoterExgressDTO creator,
        List<VotingQuestionDTO> questions) {

    public VotingSessionExgressDTO(VotingSessionEntity vse, VoterEntity creator) {
        this(vse.getSessionTitle(), new VoterExgressDTO(creator), vse.getQuestions().stream().map(VotingQuestionDTO::new).toList());
    }
}
