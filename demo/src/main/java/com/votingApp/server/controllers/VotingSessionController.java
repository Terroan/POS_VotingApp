package com.votingApp.server.controllers;

import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionDTO;
import com.votingApp.server.dtos.VotingSessionWithPasswordDTO;
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

    // ---------CRUD---------

    //Post (create) a new session
    @PostMapping("session")
    public ResponseEntity<VotingSessionDTO> createVotingSession(@RequestBody VotingSessionWithPasswordDTO votingSessionWithPasswordDTO) {
        try {
            return ResponseEntity.ok(votingSessionService.create(votingSessionWithPasswordDTO));
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //Post results to a session, return 404 if session not found
    @PostMapping("session/{sessionID}/results")
    public ResponseEntity<VotingPostDTO> postResults(@PathVariable String sessionID, @RequestBody VotingPostDTO votingPostDTO) {
        try {
            VotingSessionDTO votingSessionDTO = votingSessionService.read(sessionID);
            if (votingSessionDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(votingSessionService.postResults(sessionID, votingPostDTO));
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //Get a specific session by id
    @GetMapping("session/{id}")
    public ResponseEntity<VotingSessionDTO> readVotingSession(@PathVariable String id) {
        VotingSessionDTO votingSessionDTO = votingSessionService.read(id);
        if (votingSessionDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(votingSessionDTO);
    }

    //Get all sessions in the repo
    @GetMapping("sessions")
    public List<VotingSessionDTO> readVotingSessions() {
        return votingSessionService.readAll();
    }

    //Delete a specific session from the repo
    @DeleteMapping("session/{sessionID}/{password}")
    public ResponseEntity<Long> deleteVotingSession(@PathVariable String sessionID, @PathVariable String password) {
        VotingSessionDTO votingSessionDTO = votingSessionService.read(sessionID);
        if (votingSessionDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if(votingSessionService.checkPassword(sessionID, password) == false) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
         return ResponseEntity.ok(votingSessionService.delete(votingSessionDTO.id()));
    }

    @DeleteMapping("sessions")
    public ResponseEntity<Long> deleteVotingSessions() {
        return ResponseEntity.ok(votingSessionService.deleteAll());
    }

    //Update session
    @PutMapping("session/{sessionID}")
    public  ResponseEntity<VotingSessionDTO> updateVotingSession(@PathVariable String sessionID, @RequestBody VotingSessionWithPasswordDTO votingSessionWithPasswordDTO) {
        try {
            VotingSessionDTO votingSessionDTO = votingSessionService.read(sessionID);
            if (votingSessionDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            if(votingSessionService.checkPassword(sessionID, votingSessionWithPasswordDTO.password()) == false) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            return ResponseEntity.ok(votingSessionService.update(sessionID, votingSessionWithPasswordDTO));
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
