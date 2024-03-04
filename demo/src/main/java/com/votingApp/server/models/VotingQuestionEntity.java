package com.votingApp.server.models;

import org.bson.types.ObjectId;

import java.util.List;

public class VotingQuestionEntity {
    private ObjectId id;
    private String question;
    private List<String> options;

    public VotingQuestionEntity() {

    }

    public VotingQuestionEntity(ObjectId id, String question,List<String> options) {
        this.id = id;
        this.question = question;
        this.options = options;
    }

    public ObjectId getId() {
        return id;
    }

    public VotingQuestionEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public VotingQuestionEntity setQuestion(String question) {
        this.question = question;
        return this;
    }

    public List<String> getOptions() {
        return options;
    }

    public VotingQuestionEntity setOptions(List<String> options) {
        this.options = options;
        return this;
    }

    @Override
    public String toString() {
        return "VotingQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}
