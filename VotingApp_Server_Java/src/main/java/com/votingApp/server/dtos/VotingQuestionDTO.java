package com.votingApp.server.dtos;

import com.votingApp.server.models.VotingQuestionEntity;

import java.util.List;

public record VotingQuestionDTO(
        String question,
        List<String> options) {

    public VotingQuestionDTO(VotingQuestionEntity vqe) {
        this(vqe.getQuestion(), vqe.getOptions());
    }

    public VotingQuestionEntity toVotingQuestionEntity() {
        return new VotingQuestionEntity(question, options);
    }
}
