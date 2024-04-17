package com.votingApp.server.dtos;

import com.votingApp.server.models.VotingQuestionEntity;
import com.votingApp.server.models.VotingSessionEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public record VotingSessionIngressDTO(
        String title,
        List<VotingQuestionDTO> questions) {

    public VotingSessionIngressDTO(VotingSessionEntity vse) {
        this(vse.getSessionTitle(), vse.getQuestions().stream().map(VotingQuestionDTO::new).toList());
    }

    public VotingSessionEntity toVotingSessionEntity() {
        ArrayList<VotingQuestionEntity> questionEntities = new ArrayList<>();
        for (VotingQuestionDTO questionDTO : questions) {
            questionEntities.add(questionDTO.toVotingQuestionEntity());
        }
        return new VotingSessionEntity(new ObjectId(), title, questionEntities);
    }
}
