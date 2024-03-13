package com.votingApp.server.dtos;

public record VotingSessionWithPasswordDTO(
        VotingSessionDTO votingSessionDTO,
        String password) {

    @Override
    public VotingSessionDTO votingSessionDTO() {
        return votingSessionDTO;
    }

    @Override
    public String password() {
        return password;
    }
}
