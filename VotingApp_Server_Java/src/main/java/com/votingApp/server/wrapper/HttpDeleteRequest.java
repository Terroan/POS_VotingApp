package com.votingApp.server.wrapper;

import com.votingApp.server.dtos.VoterIngressDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;
import org.bson.types.ObjectId;

public class HttpDeleteRequest {
    private ObjectId id;
    private VoterIngressDTO voterIngressDTO;

    public HttpDeleteRequest(ObjectId id, VoterIngressDTO voterIngressDTO) {
        this.id = id;
        this.voterIngressDTO = voterIngressDTO;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public VoterIngressDTO getVoterIngressDTO() {
        return voterIngressDTO;
    }

    public void setVoterIngressDTO(VoterIngressDTO voterIngressDTO) {
        this.voterIngressDTO = voterIngressDTO;
    }

    @Override
    public String toString() {
        return "HttpDeleteRequest{" +
                "id=" + id +
                ", voterIngressDTO=" + voterIngressDTO +
                '}';
    }
}
