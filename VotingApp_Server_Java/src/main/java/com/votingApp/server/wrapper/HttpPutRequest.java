package com.votingApp.server.wrapper;

import com.votingApp.server.dtos.VoterIngressDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;
import org.bson.types.ObjectId;

public class HttpPutRequest {
    private VotingSessionIngressDTO votingSessionIngressDTO;
    private VoterIngressDTO voterIngressDTO;

    public HttpPutRequest(VotingSessionIngressDTO votingSessionIngressDTO, VoterIngressDTO voterIngressDTO) {
        this.votingSessionIngressDTO = votingSessionIngressDTO;
        this.voterIngressDTO = voterIngressDTO;
    }

    public VotingSessionIngressDTO getVotingSessionIngressDTO() {
        return votingSessionIngressDTO;
    }

    public void setVotingSessionIngressDTO(VotingSessionIngressDTO votingSessionIngressDTO) {
        this.votingSessionIngressDTO = votingSessionIngressDTO;
    }

    public VoterIngressDTO getVoterIngressDTO() {
        return voterIngressDTO;
    }

    public void setVoterIngressDTO(VoterIngressDTO voterIngressDTO) {
        this.voterIngressDTO = voterIngressDTO;
    }

    @Override
    public String toString() {
        return "HttpPutRequest{" +
                "votingSessionIngressDTO=" + votingSessionIngressDTO +
                ", voterIngressDTO=" + voterIngressDTO +
                '}';
    }
}
