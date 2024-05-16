package com.votingApp.server.wrapper;

import com.votingApp.server.dtos.VoterIngressDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;

public class HttpPostRequest {
    private VotingSessionIngressDTO votingSession;
    private VoterIngressDTO voter;

    public HttpPostRequest(VotingSessionIngressDTO votingSession, VoterIngressDTO voter) {
        this.votingSession = votingSession;
        this.voter = voter;
    }

    public VotingSessionIngressDTO getVotingSessionDTO() {
        return votingSession;
    }

    public void setVotingSessionDTO(VotingSessionIngressDTO votingSessionIngressDTO) {
        this.votingSession = votingSessionIngressDTO;
    }

    public VoterIngressDTO getVoterDTO() {
        return voter;
    }

    public void setVoterDTO(VoterIngressDTO voterIngressDTO) {
        this.voter = voterIngressDTO;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "votingSession##=" + votingSession +
                ", voter=" + voter +
                '}';
    }
}
