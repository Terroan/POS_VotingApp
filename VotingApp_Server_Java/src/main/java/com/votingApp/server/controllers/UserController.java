package com.votingApp.server.controllers;

import com.votingApp.server.dtos.VoterIngressDTO;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VotingSessionController.class);

    private final IUserService userService;

    public UserController(IUserService userService) {

        this.userService = userService;
    }

    // ---------CRUD---------
    @PostMapping("user/create")
    public ResponseEntity<VoterIngressDTO> signUp(@RequestBody VoterIngressDTO voterIngressDTO) {
        try {
            boolean tmp = userService.addUser(voterIngressDTO.toVoterEntity());
            if(!tmp)
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return ResponseEntity.ok(voterIngressDTO);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("user/login")
    public ResponseEntity<VoterIngressDTO> login(@RequestBody VoterIngressDTO voterIngressDTO) {
        try {
            VoterEntity tmp = userService.checkUser(voterIngressDTO.toVoterEntity());
            if(tmp == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok(voterIngressDTO);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
