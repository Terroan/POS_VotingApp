package com.votingApp.server.dtos;

import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingQuestionEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public record VotingSessionExgressDTO(
        String id,
        String title,
        String creator,
        List<VotingQuestionDTO> questions,
        List<VotingPostDTO> results) {

    public VotingSessionExgressDTO(VotingSessionEntity vse) {
        this(vse.getId().toString(), vse.getSessionTitle(), vse.getCreator(), vse.getQuestions().stream().map(VotingQuestionDTO::new).toList(),
                vse.getResults().stream().map(VotingPostDTO::new).toList());
    }
}
