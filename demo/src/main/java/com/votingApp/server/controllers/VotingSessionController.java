package com.votingApp.server.controllers;

import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.services.IVotingSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VotingSessionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VotingSessionController.class);

    private final IVotingSessionService votingSessionService;

    public VotingSessionController(IVotingSessionService votingSessionService) {

        this.votingSessionService = votingSessionService;
    }

    @PostMapping("session")
    @ResponseStatus(HttpStatus.CREATED)
    public VotingSessionDTO createVotingSession(@RequestBody VotingSessionDTO votingSessionDTO) {
        return votingSessionService.create(votingSessionDTO);
    }

    @GetMapping("session/{id}")
    public ResponseEntity<VotingSessionDTO> readVotingSession(@PathVariable String id) {
        VotingSessionDTO votingSessionDTO = votingSessionService.read(id);
        if (votingSessionDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(votingSessionDTO);
    }

    @GetMapping("sessions")
    public List<VotingSessionDTO> readVotingSessions() {
        return votingSessionService.readAll();
    }

    @DeleteMapping("session/{id}")
    public void deleteVotingSession(@PathVariable String id) {
         votingSessionService.delete(id);
    }

    @PutMapping("session")
    public  VotingSessionDTO updateVotingSession(@RequestBody VotingSessionDTO votingSessionDTO) {
        return votingSessionService.update(votingSessionDTO);
    }
}
