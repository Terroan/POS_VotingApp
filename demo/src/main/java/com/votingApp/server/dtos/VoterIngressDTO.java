package com.votingApp.server.dtos;

import com.votingApp.server.models.VoterEntity;
import org.bson.types.ObjectId;

public record VoterIngressDTO(
        String name,
        String password) {

    public VoterIngressDTO(VoterEntity ve) {
        this(ve.getName(), ve.getPassword());
    }

    public VoterEntity toVoterEntity() {
        return new VoterEntity(new ObjectId(), name, password);
    }
}
