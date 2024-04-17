package com.votingApp.server.controllers;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;
import com.votingApp.server.services.IVotingSessionService;
import com.votingApp.server.wrapper.HttpRequest;
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

    // ---------CRUD---------

    //Post (create) a new session
    @PostMapping("session")
    public ResponseEntity<VotingSessionIngressDTO> createVotingSession(@RequestBody VotingSessionIngressDTO votingSessionIngressDTO) {
        try {
            return ResponseEntity.ok(votingSessionService.create(votingSessionIngressDTO));
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //Post results to a session, return 404 if session not found
    @PostMapping("session/{sessionID}/results")
    public ResponseEntity<VotingPostDTO> postResults(@PathVariable String sessionID, @RequestBody VotingPostDTO votingPostDTO) {
        try {
            VotingSessionIngressDTO votingSessionIngressDTO = votingSessionService.read(sessionID);
            if (votingSessionIngressDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(votingSessionService.postResults(sessionID, votingPostDTO));
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //Get a specific session by id
    @GetMapping("session/{id}")
    public ResponseEntity<VotingSessionIngressDTO> readVotingSession(@PathVariable String id) {
        VotingSessionIngressDTO votingSessionIngressDTO = votingSessionService.read(id);
        if (votingSessionIngressDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(votingSessionIngressDTO);
    }

    //Get all sessions in the repo
    @GetMapping("sessions")
    public List<VotingSessionIngressDTO> readVotingSessions() {
        return votingSessionService.readAll();
    }

    //Delete a specific session from the repo
    @DeleteMapping("session")
    public ResponseEntity<Long> deleteVotingSession(@RequestBody HttpRequest request) {
        VotingSessionIngressDTO votingSessionIngressDTO = votingSessionService.read(sessionID);
        if (votingSessionIngressDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if(votingSessionService.checkPassword(sessionID, password) == false) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
         return ResponseEntity.ok(votingSessionService.delete(votingSessionIngressDTO.id()));
    }

    @DeleteMapping("sessions")
    public ResponseEntity<Long> deleteVotingSessions() {
        return ResponseEntity.ok(votingSessionService.deleteAll());
    }

    //Update session
    @PutMapping("session/{sessionID}")
    public  ResponseEntity<VotingSessionIngressDTO> updateVotingSession(@PathVariable String sessionID, @RequestBody VotingSessionWithPasswordDTO votingSessionWithPasswordDTO) {
        try {
            VotingSessionIngressDTO votingSessionIngressDTO = votingSessionService.read(sessionID);
            if (votingSessionIngressDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            if(votingSessionService.checkPassword(sessionID, votingSessionWithPasswordDTO.password()) == false) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            return ResponseEntity.ok(votingSessionService.update(sessionID, votingSessionWithPasswordDTO));
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
