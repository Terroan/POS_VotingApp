package com.votingApp.server.wrapper;

import com.votingApp.server.dtos.VoterIngressDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;

public class HttpPostRequest {
    private VotingSessionIngressDTO votingSessionIngressDTO;
    private VoterIngressDTO voterIngressDTO;

    public HttpPostRequest(VotingSessionIngressDTO votingSessionIngressDTO, VoterIngressDTO voterIngressDTO) {
        this.votingSessionIngressDTO = votingSessionIngressDTO;
        this.voterIngressDTO = voterIngressDTO;
    }

    public VotingSessionIngressDTO getVotingSessionDTO() {
        return votingSessionIngressDTO;
    }

    public void setVotingSessionDTO(VotingSessionIngressDTO votingSessionIngressDTO) {
        this.votingSessionIngressDTO = votingSessionIngressDTO;
    }

    public VoterIngressDTO getVoterDTO() {
        return voterIngressDTO;
    }

    public void setVoterDTO(VoterIngressDTO voterIngressDTO) {
        this.voterIngressDTO = voterIngressDTO;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "votingSessionDTO=" + votingSessionIngressDTO +
                ", voterDTO=" + voterIngressDTO +
                '}';
    }
}
