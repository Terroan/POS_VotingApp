package com.votingApp.server.models;

import org.bson.types.ObjectId;

import java.util.List;

public class VotingSessionEntity {
    private ObjectId id;
    private VoterEntity creator;
    private List<VotingQuestionEntity> questions;
    private List<VotingPostEntity> results = null;

    public VotingSessionEntity() {

    }

    public VotingSessionEntity(ObjectId id, VoterEntity creator, List<VotingQuestionEntity> questions) {
        this.id = id;
        this.creator = creator;
        this.questions = questions;
    }

    public ObjectId getId() {
        return id;
    }

    public VotingSessionEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public VoterEntity getCreator() {
        return creator;
    }

    public VotingSessionEntity setCreator(VoterEntity creator) {
        this.creator = creator;
        return this;
    }

    public List<VotingQuestionEntity> getQuestions() {
        return questions;
    }

    public VotingSessionEntity setQuestions(List<VotingQuestionEntity> questions) {
        this.questions = questions;
        return this;
    }

    public List<VotingPostEntity> getResults() {
        return results;
    }

    public VotingSessionEntity setResults(List<VotingPostEntity> results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return "VotingSession{" +
                "id=" + id +
                ", creator=" + creator +
                ", questions=" + questions +
                ", results=" + results +
                '}';
    }
}
