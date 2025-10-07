package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.repositories.ProjectStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class ProjectStatusController {

    @Autowired
    private ProjectStatusRepository psRepository;

    @GetMapping("/all")
    public Iterable<ProjectStatus> getAllUsers() {
        return psRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveStatus(@RequestBody ProjectStatus status) {
        psRepository.save(status);
        return new ResponseEntity<String>("Saved!", HttpStatus.OK);
    }
}
