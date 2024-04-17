package com.votingApp.server.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class VotingSessionEntity {
    private ObjectId id;
    private String sessionID;
    private String sessionTitle;
    private List<VotingQuestionEntity> questions;
    private List<VotingPostEntity> results;

    public VotingSessionEntity() {

    }

    public VotingSessionEntity(ObjectId id, String sessionTitle, ArrayList<VotingQuestionEntity> questions) {
        this.id = id;
        this.sessionTitle = sessionTitle;
        this.questions = questions;
        this.results = new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
    }

    public VotingSessionEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
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
}