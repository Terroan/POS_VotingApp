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

import java.nio.file.Path;
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
    @ResponseStatus(HttpStatus.CREATED)
    public VotingSessionDTO createVotingSession(@RequestBody VotingSessionWithPasswordDTO votingSessionWithPasswordDTO) {
        System.out.println(votingSessionWithPasswordDTO);
        return votingSessionService.create(votingSessionWithPasswordDTO);
    }

    //Post results to a session, return 404 if session not found
    @GetMapping("session/{id}/results")
    public ResponseEntity<VotingPostDTO> postResults(@PathVariable String id, @RequestBody VotingPostDTO votingPostDTO) {
        VotingSessionDTO votingSessionDTO = votingSessionService.read(id);
        if (votingSessionDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        votingSessionService.postResults(id, votingPostDTO);
        return ResponseEntity.ok(votingPostDTO);
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
    @DeleteMapping("session/{id}/{password}")
    public ResponseEntity<Long> deleteVotingSession(@PathVariable String id, @PathVariable String password) {
        VotingSessionDTO votingSessionDTO = votingSessionService.read(id);
        if (votingSessionDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if(votingSessionService.checkPassword(id, password) == false) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
         return ResponseEntity.ok(votingSessionService.delete(id));
    }

    @DeleteMapping("sessions")
    public ResponseEntity<Long> deleteVotingSessions() {
        return ResponseEntity.ok(votingSessionService.deleteAll());
    }

    //Update session
    @PutMapping("session/{id}/{privateKey}")
    public  VotingSessionDTO updateVotingSession(@RequestBody VotingSessionDTO votingSessionDTO) {
        return votingSessionService.update(votingSessionDTO);
    }
}
