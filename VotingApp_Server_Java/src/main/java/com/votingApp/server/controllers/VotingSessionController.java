package com.votingApp.server.controllers;

import com.votingApp.server.dtos.VoterIngressDTO;
import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionExgressDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;
import com.votingApp.server.services.IVotingSessionService;
import com.votingApp.server.wrapper.HttpPostRequest;
import org.bson.types.ObjectId;
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

    // Return "up" to see if server is active
    @GetMapping("status")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }

    //Post (create) a new session
    @PostMapping("session")
    public ResponseEntity<String> createVotingSession(@RequestBody HttpPostRequest httpRequest) {
        try {
            ObjectId tmp = votingSessionService.create(httpRequest.getVotingSessionDTO(), httpRequest.getVoterDTO());
            if(tmp == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok(tmp.toString());
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Start a session
    @PostMapping("session/start/{id}")
    public ResponseEntity<String> startVotingSession(@PathVariable String id, @RequestBody VoterIngressDTO voterIngressDTO) {
        try {
            String tmp = votingSessionService.startSession(new ObjectId(id), voterIngressDTO);
            System.out.println(tmp);
            if(tmp == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok(tmp);
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // end session
    @PostMapping("session/end/{id}")
    public ResponseEntity<VotingSessionExgressDTO> endSession(@PathVariable String id, @RequestBody VoterIngressDTO voterIngressDTO) {
        try {
            VotingSessionExgressDTO tmp = votingSessionService.endSession(new ObjectId(id), voterIngressDTO);
            if(tmp == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            System.out.println(tmp);
            return ResponseEntity.ok(tmp);
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //Post results to a session, return 404 if session not found
    @PostMapping("session/results/{sessionID}")
    public ResponseEntity<VotingPostDTO> postResults(@PathVariable String sessionID, @RequestBody VotingPostDTO votingPostDTO) {
        try {
            VotingSessionExgressDTO votingSessionExgressDTO = votingSessionService.read(sessionID);
            if (votingSessionExgressDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(votingSessionService.postResults(sessionID, votingPostDTO));
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // get all sessions for a specific user
    @PostMapping("sessions/user")
    public ResponseEntity<List<VotingSessionExgressDTO>> readUserSessions(@RequestBody VoterIngressDTO voterIngressDTO) {
        List<VotingSessionExgressDTO> votingSessionExgressDTOs = votingSessionService.readAllByUser(voterIngressDTO);
        if (votingSessionExgressDTOs == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(votingSessionExgressDTOs);
    }

    //Get a specific session by id
    @GetMapping("session/{id}")
    public ResponseEntity<VotingSessionExgressDTO> readVotingSession(@PathVariable String id) {
        VotingSessionExgressDTO votingSessionExgressDTO = votingSessionService.read(id);
        if (votingSessionExgressDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(votingSessionExgressDTO);
    }

    //Get all sessions in the repo
    @GetMapping("sessions")
    public List<VotingSessionIngressDTO> readVotingSessions() {
        return votingSessionService.readAll();
    }

    //Delete a specific session from the repo
    @DeleteMapping("session/{id}")
    public ResponseEntity<Boolean> deleteVotingSession(@PathVariable String id, @RequestBody VoterIngressDTO voterIngressDTO) {
        boolean tmp = votingSessionService.delete(new ObjectId(id), voterIngressDTO.toVoterEntity());
        if (!tmp) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // session id is not in users list
        return ResponseEntity.ok(tmp);
    }

    // Delete all sessions
    @DeleteMapping("sessions")
    public ResponseEntity<Long> deleteVotingSessions() {
        return ResponseEntity.ok(votingSessionService.deleteAll());
    }

    //Update session
    @PutMapping("session/{id}")
    public  ResponseEntity<HttpPostRequest> updateVotingSession(@PathVariable String id, @RequestBody HttpPostRequest httpPostRequest) {
        try {
            boolean tmp = votingSessionService.update(new ObjectId(id), httpPostRequest.getVotingSessionDTO().toVotingSessionEntity(),
                    httpPostRequest.getVoterDTO().toVoterEntity());
            if (!tmp) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // session id is not in users list
            return ResponseEntity.ok(httpPostRequest);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}