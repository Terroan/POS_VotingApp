package com.votingApp.server.dtos;

import com.votingApp.server.models.VoterEntity;

public record VoterExgressDTO(
        String name) {

    public VoterExgressDTO(VoterEntity ve) {
        this(ve.getName());
    }
}
