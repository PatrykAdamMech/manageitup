package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("participant")
public class ProjectParticipantController {

    @Autowired
    private ProjectParticipantRepository ppRepository;

    @GetMapping("/all")
    public Iterable<ProjectParticipant> getAllUsers() {
        return ppRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveParticipant(@RequestBody ProjectParticipant participant) {
        ppRepository.save(participant);
        return new ResponseEntity<String>("Saved!", HttpStatus.OK);
    }
}
